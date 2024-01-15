package com.kj.products.product.dto;

import com.kj.productCategory.entity.ProductCategory;
import lombok.*;

@Getter
@Setter
@ToString
public class ProductCategoryUpdateDto {
    private Long id;
    private int mainProductCategoryId;
    private String mainProductCategoryName;
    private String subProductCategoryName;

//    @QueryProjection
//    public ProductCategoryUpdateDto(Long id, int mainProductCategoryId, String mainProductCategoryName, String subProductCategoryName) {
//        this.id = id;
//        this.mainProductCategoryId = mainProductCategoryId;
//        this.mainProductCategoryName = mainProductCategoryName;
//        this.subProductCategoryName = subProductCategoryName;
//    }

    @Builder
    public ProductCategoryUpdateDto(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.mainProductCategoryId = productCategory.getMainProductCategoryId();
        this.mainProductCategoryName = productCategory.getMainProductCategoryName();
        this.subProductCategoryName = productCategory.getSubProductCategoryName();
    }
}

