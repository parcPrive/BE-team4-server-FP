package com.kj.product;

import com.kj.product.dto.ProductInputDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/insert")
    public String InsertProduct(
            Model model
    ){
        int result = productService.findByMaxProductId();
        log.info("result ===>> {}", result);
        model.addAttribute("productMax",result);
        return "/product/insert";
    }

    @PostMapping("/insert")
    @ResponseBody
    public String InsertProductProcess(
            @ModelAttribute ProductInputDto productInputDto
            ){
        log.info("insertProdudtProcess===>> {} ", productInputDto);
        return "asd";
    }

    @PostMapping("/detailimage/{no}")
    public String ckEditorDetailImage(
            @RequestParam("upload") MultipartFile productDetailImage,
            @PathVariable int no
            ) {
        productService.S3UploadProductDetailImage(productDetailImage, no);
        return null;
    }


}
