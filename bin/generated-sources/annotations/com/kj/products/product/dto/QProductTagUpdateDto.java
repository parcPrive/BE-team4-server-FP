package com.kj.products.product.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.kj.products.product.dto.QProductTagUpdateDto is a Querydsl Projection type for ProductTagUpdateDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductTagUpdateDto extends ConstructorExpression<ProductTagUpdateDto> {

    private static final long serialVersionUID = 558886901L;

    public QProductTagUpdateDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> productTag) {
        super(ProductTagUpdateDto.class, new Class<?>[]{long.class, String.class}, id, productTag);
    }

}

