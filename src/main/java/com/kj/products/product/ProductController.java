package com.kj.products.product;


import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;
import com.kj.config.ClientConf;
import com.kj.productCategory.ProductCategoryService;
import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.products.product.dto.*;
import com.kj.products.productCart.ProductCartService;
import com.kj.products.productCart.dto.ProductCartInsertDto;
import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productElasticSearch.ProductSearchService;
import com.kj.products.productElasticSearch.dto.ESProductReturnDto;
import com.kj.products.productElasticSearch.entity.ProductDocument;
import com.kj.products.productOder.ProductOderservice;
import com.kj.products.productOder.dto.ProductCartOrderDto;
import com.kj.products.productOder.dto.ProductOrderInfoDto;
import com.kj.products.productOder.dto.ProductOrderSuccessDto;
import com.kj.products.productOder.entity.MyProductOrderDto;
import com.kj.products.productReview.dto.ProductinsertReviewDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import static org.springframework.util.StringUtils.hasText;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductCartService productCartService;
    private final ProductOderservice productOderservice;
    private final ProductSearchService productSearchService;

    private final ClientConf client;
// ================상품 등록 관련========================
    @GetMapping("/insert")
    public String InsertProduct(
            Model model
    ) {
        int result = productService.findByMaxProductId();
        String bucketName = String.valueOf(UUID.randomUUID());
        List<ProductCategoryfindDto> productCategories =productCategoryService.findAllProductCategory();
        log.info("result ===>> {}", result);
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("productCategory",productCategories);
        return "/product/insert";
    }

    @PostMapping("/insert")
    public String InsertProductProcess(
            @ModelAttribute ProductInputDto productInputDto
    ) throws IOException {
        log.info("insertProdudtProcess===>> {} ", productInputDto);
        productService.insertProduct(productInputDto);
        return "/product/insert";
    }

    @PostMapping("/detailimage/{bucketName}")
    @ResponseBody
    public Map<String, Object> ckEditorDetailImage(
            @RequestParam("upload") MultipartFile productDetailImage,
            @PathVariable String bucketName
    ) {
        Map<String, Object> data = new HashMap<>();
        try{
            String s3Url = productService.S3UploadProductDetailImage(productDetailImage, bucketName);
            data.put("uploaded", true);
            data.put("url", s3Url);
            return data;
        }catch (IOException e){
            data.put("uploaded", false);
            return data;
        }
    }
    // ================상품 업데이트 관련========================

    @GetMapping("/update/{no}")
    public String updateProduct(
            Model model,
            @PathVariable int no
    ){
        log.info("번호번호번호 ===>>>>> {}" , no);
        String bucketName = String.valueOf(UUID.randomUUID());
        ProductUpdateDto productUpdateDto = productService.findByProductId(no);
        model.addAttribute("productUpdateDto", productUpdateDto);
        model.addAttribute("newBucketName", bucketName);
        return "product/update";
    }

    @PostMapping("/update/{no}")
    public String updateProductProcess(
            @PathVariable int no,
            @ModelAttribute ProductUpdateInputDto productUpdateInputDto
            ) throws IOException {
        log.info("no ===>>> {}", no);
        log.info("productInputDto ===>>> {}", productUpdateInputDto);
        productService.updateProduct(no,productUpdateInputDto);
        return "redirect:/product/update/" + no;
    }


    @GetMapping("/list/{page}")
    public String findListProduct(
            @PathVariable int page,
            @ModelAttribute ProductSearchCondotion productSearchCondotion,
            Model model
    ) throws IOException {

        log.info("page ==>>> {}", page);
        log.info("productSeach ==>> {}", productSearchCondotion);
        // 카테고리별 검색
        if(hasText(productSearchCondotion.getCategory())) {
            if (productSearchCondotion.getCategory().equals("all")) {
                Map<String, Object> searchResult = productSearchService.elasticSearchCategoryAll(productSearchCondotion.getSearchWord(), page);
                log.info("엘라스틱 결과 ===>> {}", searchResult.get("productList"));
                log.info("엘라스틱 결과 ===>> {}", searchResult.get("page"));
                model.addAttribute("products", searchResult.get("productList"));
                model.addAttribute("page", searchResult.get("page"));
                return "/product/eslist";
            }else if(productSearchCondotion.getCategory().equals("상의") || productSearchCondotion.getCategory().equals("하의") || productSearchCondotion.getCategory().equals("신발")){
                Map<String, Object> searchResult = productSearchService.elasticSearchCategory(page, productSearchCondotion);
                model.addAttribute("products", searchResult.get("productList"));
                model.addAttribute("page", searchResult.get("page"));
                return "/product/eslist";
            }
        }

        int elasticSearchPage = 0;
        //상품 페이지의 첫화면 레디스에 없다면 엘라스틱서치에서 찾는다 찾은값 레디스에 저장
        if(page == 1 && productSearchCondotion.getCategory() == null) {
            // map의 키값은 productList, page이다.
            Map<String,Object> productMainMap = productSearchService.productListMain(page);
            elasticSearchPage = (int) productMainMap.get("page");
            if(elasticSearchPage != 0){
                model.addAttribute("products", productMainMap.get("productList"));
                model.addAttribute("page", productMainMap.get("page"));
                log.info("여기 ??? -====>> {}", productMainMap.get("productList"));
                return "/product/eslist";
            }

        }

        // 레디스 엘라스틱서치 둘다 없다면 디비에서 조회
        if(elasticSearchPage == 0){
            PageImpl<ProductListDto> productList =  productService.findListProductPage(page, productSearchCondotion);
            int productListPage = productList.getTotalPages();

            model.addAttribute("products", productList);
            model.addAttribute("productListPage", productListPage);
        }
        return "/product/list";
    }


    @GetMapping("/view/{productId}")
    public String productView(
            @PathVariable int productId,
            @RequestParam int reviewPage,
            @RequestParam int qnaPage,
            HttpServletRequest req, // 상품조회 수 쿠키이름 => productClick
            HttpServletResponse res,
            Model model
    ){

        ProductFindOneDto productFindOneDto = productService.findOneByProductId(productId, reviewPage, qnaPage, req, res);
        model.addAttribute("findOneProduct", productFindOneDto);
        return "/product/view";

    }

// ===========장바구니============
    @GetMapping("/cart/{userId}")
    public String productCart(
            @PathVariable String userId,
            Model model
    ){
        log.info("userId ===>>> {}", userId);
        List<ProductCartListDto> productCartList = productCartService.findByUserId(userId);
        model.addAttribute("productCartList",productCartList);
        return "/product/cart";
    }


    @PostMapping("/cart")
    @ResponseBody
    public Long productCartProcess(
            @ModelAttribute ProductCartInsertDto productCartInsertDto
    ){
        log.info("productCartInsertDto ====>>> {}",productCartInsertDto);
        Long productCartCount = productCartService.insertProductCart(productCartInsertDto);
        return productCartCount;
    }

    // ============== 주문내역=========

    @PostMapping("/order")
    public String productOrder(
            @ModelAttribute ProductCartOrderDto productCartOrderDto,
            Model model
    ){
        log.info("productCartOrderDto ===>>> {}", productCartOrderDto);
        List<ProductOrderInfoDto> productOrderInfoList = productCartService.findByUserNickInProductCartId(productCartOrderDto);
        model.addAttribute("productOrderInfo", productOrderInfoList);
        return "/product/order";
    }

    @PostMapping("/deletecart")
    @ResponseBody
    public String productCartDeleteProcess(
            @RequestParam Long productCartId
    ){
        log.info("여기는 들어오나요??");
        log.info("productCartID ===>>> {}", productCartId);
        Long aa= 12L;
        productCartService.deleteByProductCartId(productCartId);
        return "success";

    }


    //=========결제내역, 주문내역=========
    @GetMapping("/paymentsuccess")
    public String productPaymentSuccess(
            @RequestParam List<Long> orderIds,
            Model model
    ){
        List<ProductOrderSuccessDto> productOrderSuccessList = productOderservice.findProductOrdersByOrderId(orderIds);
        model.addAttribute("productOrderSuccessList",productOrderSuccessList);
        return "/product/paymentsuccess";
    }

    // 내주문내역에서 환불처리
    @GetMapping("/myorderlist/{userNickName}")
    public String myOrderList(
            @PathVariable String userNickName,
            Model model
    ){
        List<MyProductOrderDto> myProductOrders = productOderservice.findProductOrdersByUserNickName(userNickName);
        model.addAttribute("myProductOrders", myProductOrders);
        return "/product/myorderlist";
    }


    @GetMapping("/datainsert")
    public String dataInsert(
            Model model
    ){
        int result = productService.findByMaxProductId();
        String bucketName = String.valueOf(UUID.randomUUID());
        List<ProductCategoryfindDto> productCategories =productCategoryService.findAllProductCategory();
        log.info("result ===>> {}", result);
        model.addAttribute("bucketName", bucketName);
        model.addAttribute("productCategory",productCategories);
        return "/product/test";
    }

    @PostMapping("/datainsert")
    public String dataInsert(
            @ModelAttribute ProductInputDto productInputDto
    ) throws IOException {
        log.info("insertProdudtProcess===>> {} ", productInputDto);
        productService.insertTest(productInputDto);
        return "/product/insert";
    }


    @GetMapping("/search3")
    @ResponseBody
    public List<ProductDocument> search3() throws IOException {

//        String aaa = """
//                {
//                    "query":{
//                        "match": {
//                            "product_name": "123"
//                        }
//                    }
//                }
//                """;
//        Reader input = new StringReader(aaa);
//        IndexRequest<JsonData> requerst = IndexRequest.of(i -> i
//                .index("myproduct03")
//                .withJson(input));
//        IndexResponse response = client.client.index(requerst);
//        log.info("여기로 ?? ===>>> {}", response.index());
        String aa = "반";

        SearchResponse<ProductDocument> search = client.client.search(s -> s
                        .index("myproduct09")
                        .query(q -> q
                                .term(t -> t
                                        .field("product_name")
                                        .value(v -> v.stringValue(String.valueOf(aa)))
                                )),
                ProductDocument.class);
//        client.client.
        List<ProductDocument> result = new ArrayList<>();
        Long total = search.hits().total().value(); // 총갯수
        List<Hit<ProductDocument>> hits =  search.hits().hits();
        for(Hit<ProductDocument> hit : hits){
            result.add(hit.source());
        }
        log.info("결과 ===>>> {}", result);
        return result;

    }


}