package com.kj.product;

import com.kj.product.dto.ProductInputDto;
import com.kj.productCategory.ProductCategoryService;
import com.kj.productCategory.dto.ProductCategoryfindDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    @GetMapping("/insert")
    public String InsertProduct(
            Model model
    ) {
        int result = productService.findByMaxProductId();
        List<ProductCategoryfindDto> productCategories =productCategoryService.findAllProductCategory();
        log.info("result ===>> {}", result);
        model.addAttribute("productMax", result);
        model.addAttribute("productCategory",productCategories);
        return "/product/insert";
    }

    @PostMapping("/insert")
    public String InsertProductProcess(
            @ModelAttribute ProductInputDto productInputDto
    ) {
        log.info("insertProdudtProcess===>> {} ", productInputDto);
        Map<String, MultipartFile> productImages = new HashMap<>();
        productImages.put("file1", productInputDto.getFile1());
        productImages.put("file2", productInputDto.getFile2());
        productImages.put("file3", productInputDto.getFile3());
        productImages.put("file4", productInputDto.getFile4());
        productImages.put("file5", productInputDto.getFile5());
        log.info("이미지= ===>>> {}", productInputDto.getFile1().getOriginalFilename());
        productService.insertProductImage(productInputDto);


        productService.insertProduct(productInputDto);
        return "redirect:/product/insert";
    }

    @PostMapping("/detailimage/{no}")
    @ResponseBody
    public Map<String, Object> ckEditorDetailImage(
            @RequestParam("upload") MultipartFile productDetailImage,
            @PathVariable int no
    ) {
        Map<String, Object> data = new HashMap<>();
        try{
            String s3Url = productService.S3UploadProductDetailImage(productDetailImage, no);
            data.put("uploaded", true);
            data.put("url", s3Url);
            return data;
        }catch (IOException e){
            data.put("uploaded", false);
            return data;
        }
    }
}