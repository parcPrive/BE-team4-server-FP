package com.kj.products.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kj.products.product.dto.QProductListDto is a Querydsl Projection type for ProductListDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductListDto extends ConstructorExpression<ProductListDto> {

    private static final long serialVersionUID = -4811096L;

    public QProductListDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> productName, com.querydsl.core.types.Expression<? extends java.util.List<com.kj.products.product.entity.ProductImage>> productImages, com.querydsl.core.types.Expression<Integer> productPrice) {
        super(ProductListDto.class, new Class<?>[]{long.class, String.class, java.util.List.class, int.class}, id, productName, productImages, productPrice);
    }

}

