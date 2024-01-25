package com.kj.products.productQnACategory.dto;

import com.kj.products.productQnACategory.entity.ProductQnACategory;
import lombok.Builder;
import lombok.Data;

@Data
public class ProductQnACategoryfindDto {
    private Long productQnACategoryId;
    private String productQnACategoryName;

    @Builder
    public ProductQnACategoryfindDto(ProductQnACategory productQnACategory) {
        this.productQnACategoryId = productQnACategory.getId();
        this.productQnACategoryName = productQnACategory.getProductQnACategoryName();
    }
}
