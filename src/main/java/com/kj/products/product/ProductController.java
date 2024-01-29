package com.kj.products.product;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.kj.config.ClientConf;
import com.kj.productCategory.ProductCategoryService;
import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.products.product.dto.*;
import com.kj.products.productCart.ProductCartService;
import com.kj.products.productCart.dto.ProductCartInsertDto;
import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productElasticSearch.ProductElasticSearchClient;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductCartService productCartService;
    private final ProductOderservice productOderservice;

    private final ProductElasticSearchClient productElasticSearchClient;
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
    ){
        log.info("page ==>>> {}", page);
        log.info("productSeach ==>> {}", productSearchCondotion);
        PageImpl<ProductListDto> productList =  productService.findListProductPage(page, productSearchCondotion);
        int productListPage = productList.getTotalPages();
        List<ProductListDto> products = productList.getContent();


        model.addAttribute("products", productList);
        model.addAttribute("productListPage", productListPage);
        return "/product/list";
    }


    @GetMapping("/view/{productId}")
    public String productView(
            @PathVariable int productId,
            @RequestParam int reviewPage,
            @RequestParam int qnaPage,
            HttpServletRequest req,
            Model model
    ){
        log.info("prprprprprpr =====>>> {}", productId);
        log.info("prprprprprpr =====>>> {}", reviewPage);
        log.info("prprprprprpr =====>>> {}", qnaPage);

        ProductFindOneDto productFindOneDto = productService.findOneByProductId(productId, reviewPage, qnaPage, req);
        model.addAttribute("findOneProduct", productFindOneDto);
        return "/product/view";

    }

// ===========장바구니============
    @GetMapping("/cart/{userName}")
    public String productCart(
            @PathVariable String userName,
            Model model
    ){
        log.info("userId ===>>> {}", userName);
        List<ProductCartListDto> productCartList = productCartService.findByUserId(userName);
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
    @GetMapping("/myorderlist/{usernickname}")
    public String myOrderList(
            @PathVariable String usernickname,
            Model model
    ){
        List<MyProductOrderDto> myProductOrders = productOderservice.findProductOrdersByUserNickName(usernickname);
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

    @GetMapping("/search1")
    @ResponseBody
    public String search() {
        return productElasticSearchClient.getSearchData();
    }

    @GetMapping("/search2")
    @ResponseBody
    public String search2(){
        log.info("여디!!!");
        String aaa = """
                {
                    "query":{
                        "match": {
                            "product_name": "123"
                        }
                    }
                }
                """;
//        SearchResponse<ProductDocument> response =  productElasticSearchClient.getSearchName(aaa);

        String response =  productElasticSearchClient.getSearchName(aaa);
        log.info("검색결과 ====>>> {}", productElasticSearchClient.getSearchName(aaa));

        return response;

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
//        log.info("여기로 ?? ===>>> {}", response.index().);
        int aa = 123;

        SearchResponse<ProductDocument> search = client.client.search(s -> s
                        .index("myproduct05")
                        .query(q -> q
                                .term(t -> t
                                        .field("product_name")
                                        .value(v -> v.stringValue(String.valueOf(aa)))

                                )),
                ProductDocument.class);
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