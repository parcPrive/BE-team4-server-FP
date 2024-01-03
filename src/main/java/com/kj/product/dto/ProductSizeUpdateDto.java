package com.kj.product.dto;

import com.kj.product.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class ProductSizeUpdateDto {
    private Long id;
    private String productSize;
    private int productCount;

    @QueryProjection
    public ProductSizeUpdateDto(Long id, String productSize, int productCount) {
        this.id = id;
        this.productSize = productSize;
        this.productCount = productCount;

    }
}
