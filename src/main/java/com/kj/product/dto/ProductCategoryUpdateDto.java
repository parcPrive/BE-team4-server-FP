package com.kj.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@ToString
public class ProductCategoryUpdateDto {
    private Long id;
    private int mainProductCategoryId;
    private String mainProductCategoryName;
    private String subProductCategoryName;

    @QueryProjection
    public ProductCategoryUpdateDto(Long id, int mainProductCategoryId, String mainProductCategoryName, String subProductCategoryName) {
        this.id = id;
        this.mainProductCategoryId = mainProductCategoryId;
        this.mainProductCategoryName = mainProductCategoryName;
        this.subProductCategoryName = subProductCategoryName;
    }
}

