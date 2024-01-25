package com.kj.products.productQnACategory;

import com.kj.products.productQnACategory.dto.ProductQnACategoryInputDto;
import com.kj.products.productQnACategory.dto.ProductQnACategoryfindDto;
import com.kj.products.productQnACategory.entity.ProductQnACategory;
import com.kj.products.productQnACategory.repository.ProductQnACategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQnACategoryService {
    private final ProductQnACategoryRepository productQnACategoryRepository;

    @Transactional
    public void insertProductQnACategory(ProductQnACategoryInputDto productQnACategoryInputDto){
        ProductQnACategory inputProductQnACategory = new ProductQnACategory(productQnACategoryInputDto);
        productQnACategoryRepository.save(inputProductQnACategory);
    }

    public List<ProductQnACategoryfindDto> findAllProductQnACategory() {
        List<ProductQnACategory> productQnACategories = productQnACategoryRepository.findAll();
        List<ProductQnACategoryfindDto> productQnACategoryfindList = new ArrayList<>();
        for(ProductQnACategory productQnACategory : productQnACategories){
            productQnACategoryfindList.add(new ProductQnACategoryfindDto(productQnACategory));
        }
        return productQnACategoryfindList;
    }
}
