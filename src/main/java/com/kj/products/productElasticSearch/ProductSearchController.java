package com.kj.products.productElasticSearch;

import com.kj.products.product.dto.ProductListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ProductSearchController {
    private final ProductSearchService productSearchService;

    @GetMapping("/product/categorysearch/{page}")
    public String productCategorySearch(
            @PathVariable int page,
            @ModelAttribute ProductCategorySearchCondition productCategorySearchCondition,
            Model model
    ){

        PageImpl<ProductListDto> findProducts = productSearchService.findProductByProductTag(page, productCategorySearchCondition);
        log.info("페이지 ===>>> {}", page);
        log.info("category ===>>> {}", productCategorySearchCondition);
        log.info("결과!!! ===>>> {}", findProducts.get());
        for(ProductListDto aa : findProducts){
            log.info(aa.getProductName());
        }
        model.addAttribute("products", findProducts);

        return "/product/list";
    }


    @GetMapping("/product/searchsetting")
    @ResponseBody
    public String productSearchSetting(){
        productSearchService.elasticSearchSetting();
        return "성공";
    }

    @GetMapping("/product/searchmapping")
    @ResponseBody
    public String productSearchMapping(){
        productSearchService.elasticSearchMepping();
        return "성공";
    }

}
