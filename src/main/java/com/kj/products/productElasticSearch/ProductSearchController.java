package com.kj.products.productElasticSearch;

import com.kj.products.product.dto.ProductListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductSearchController {
    private final ProductSearchService productSearchService;

    @GetMapping("/categorysearch/{page}")
    @ResponseBody
    public String productCategorySearch(
            @PathVariable int page,
            @ModelAttribute ProductCategorySearchCondition productCategorySearchCondition

    ){
        PageImpl<ProductListDto> findProducts = productSearchService.findProductByProductTag(page, productCategorySearchCondition);
        log.info("페이지 ===>>> {}", page);
        log.info("category ===>>> {}", productCategorySearchCondition);
        return "aaa";
    }

}
