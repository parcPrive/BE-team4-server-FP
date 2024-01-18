package com.kj.products.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kj.products.product.dto.QProductSizeUpdateDto is a Querydsl Projection type for ProductSizeUpdateDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductSizeUpdateDto extends ConstructorExpression<ProductSizeUpdateDto> {

    private static final long serialVersionUID = 1674000988L;

    public QProductSizeUpdateDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> productSize, com.querydsl.core.types.Expression<Integer> productCount) {
        super(ProductSizeUpdateDto.class, new Class<?>[]{long.class, String.class, int.class}, id, productSize, productCount);
    }

}

