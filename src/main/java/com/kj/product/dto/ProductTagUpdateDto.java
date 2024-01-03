package com.kj.product.dto;

import com.kj.product.entity.Product;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class ProductTagUpdateDto {

    private Long id;
    private String productTag;

    @QueryProjection
    public ProductTagUpdateDto(Long id, String productTag) {
        this.id = id;
        this.productTag = productTag;

    }
}
