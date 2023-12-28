package com.kj.productCategory.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kj.productCategory.dto.QProductCategoryfindDto is a Querydsl Projection type for ProductCategoryfindDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductCategoryfindDto extends ConstructorExpression<ProductCategoryfindDto> {

    private static final long serialVersionUID = -1862586229L;

    public QProductCategoryfindDto(com.querydsl.core.types.Expression<Long> subProductCategoryId, com.querydsl.core.types.Expression<Integer> mainProductCategoryId, com.querydsl.core.types.Expression<String> subProductCategoryName) {
        super(ProductCategoryfindDto.class, new Class<?>[]{long.class, int.class, String.class}, subProductCategoryId, mainProductCategoryId, subProductCategoryName);
    }

}

