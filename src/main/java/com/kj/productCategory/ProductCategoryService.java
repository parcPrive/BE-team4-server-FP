package com.kj.productCategory;

import com.kj.productCategory.Repository.ProductCategoryRepository;
import com.kj.productCategory.dto.ProductCategoryInputDto;
import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.productCategory.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

    /**
     *
     * @param productCategoryInputDto
     * @return
     */
    @Transactional
    public ProductCategory insertProductCategory(ProductCategoryInputDto productCategoryInputDto){
        String mainProductCategory = null;
        if(productCategoryInputDto.mainProductCategoryId() == 100) mainProductCategory = "상의";
        else if(productCategoryInputDto.mainProductCategoryId() == 200) mainProductCategory = "바지";
        else if(productCategoryInputDto.mainProductCategoryId() == 300) mainProductCategory = "아우터";
        else if(productCategoryInputDto.mainProductCategoryId() == 400) mainProductCategory = "신발";
        ProductCategory productCategory = new ProductCategory(productCategoryInputDto, mainProductCategory);
        ProductCategory result = productCategoryRepository.save(productCategory);
        log.info("result ==>> {}", result);
        return result;
    }

    public List<ProductCategoryfindDto> findAllProductCategory(){
        List<ProductCategoryfindDto> findAllProductCategory = productCategoryRepository.findAllProductCategory();
        log.info("productCategory ===>> {} ", findAllProductCategory);
        return findAllProductCategory;
    }
}
