package com.kj.productCategory.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
@Data
public class ProductCategoryfindDto {
    private Long subProductCategoryId;
    private int mainProductCategoryId;
    private String subProductCategoryName;

    @QueryProjection
    public ProductCategoryfindDto(Long subProductCategoryId, int mainProductCategoryId, String subProductCategoryName){
        this.subProductCategoryId = subProductCategoryId;
        this.mainProductCategoryId = mainProductCategoryId;
        this.subProductCategoryName = subProductCategoryName;
    }
}
