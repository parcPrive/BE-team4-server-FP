package com.kj.products.productQnACategory;

import com.kj.products.productQnACategory.dto.ProductQnACategoryInputDto;
import com.kj.products.productQnACategory.dto.ProductQnACategoryfindDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductQnACategoryController {
    private final ProductQnACategoryService productQnACategoryService;
    @GetMapping("/admin/product/insertqnacategory")
    public String insertproductqnacategory(){
        log.info("제발........아 진짜");
        return "/product/insertQnACategory";
    }

    @PostMapping("/admin/product/insertqnacategory")
    @ResponseBody
    public String insertproductqnacategoryProcess(
            @ModelAttribute ProductQnACategoryInputDto productQnACategoryInputDto
            ){
        productQnACategoryService.insertProductQnACategory(productQnACategoryInputDto);
        log.info("일단 여기 들어옴? ===>> {}", productQnACategoryInputDto);
        return "aaa";
    }

    @GetMapping("/product/findqnacategory")
    @ResponseBody
    public List<ProductQnACategoryfindDto> findAllProductQnACategory(
    ){
        List<ProductQnACategoryfindDto> productQnACategoryfinds = productQnACategoryService.findAllProductQnACategory();
        return productQnACategoryfinds;

    }
}
