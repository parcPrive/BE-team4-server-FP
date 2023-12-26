package com.kj.productCategory;

import com.kj.productCategory.Repository.ProductCategoryRepository;
import com.kj.productCategory.dto.ProductCategoryInputDto;
import com.kj.productCategory.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
@Slf4j
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;
    @GetMapping("/insertcategory")
    public String insertCategory(){
        return "/product/insertCategory";
    }

    @PostMapping("/insertcategory")
    public String insertCategoryProcess(
            @ModelAttribute ProductCategoryInputDto productCategoryInputDto
            ){

        ProductCategory result = productCategoryService.insertProductCategory(productCategoryInputDto);
        log.info("컨트롤러안의 ===>> {}", productCategoryInputDto);
        return "redirect:/product/insertcategory";
    }
}
