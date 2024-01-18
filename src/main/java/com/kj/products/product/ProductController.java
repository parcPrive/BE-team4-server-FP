package com.kj.products.product;

import com.kj.productCategory.ProductCategoryService;
import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.products.product.dto.*;
import com.kj.products.productCart.ProductCartService;
import com.kj.products.productCart.dto.ProductCartInsertDto;
import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productOder.dto.ProductCartOrderDto;
import com.kj.products.productOder.dto.ProductOrderInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;
    private final ProductCartService productCartService;
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
            Model model
    ){
        log.info("prprprprprpr =====>>> {}", productId);
        ProductFindOneDto productFindOneDto = productService.findOneByProductId(productId);
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


}