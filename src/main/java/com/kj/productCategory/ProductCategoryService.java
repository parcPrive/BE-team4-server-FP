package com.kj.productCategory;

import com.kj.productCategory.Repository.ProductCategoryRepository;
import com.kj.productCategory.dto.ProductCategoryInputDto;
import com.kj.productCategory.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        ProductCategory productCategory = new ProductCategory(productCategoryInputDto);
        ProductCategory result = productCategoryRepository.save(productCategory);
        log.info("result ==>> {}", result);
        return result;
    }
}
