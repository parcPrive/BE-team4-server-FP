package com.kj.products.product.dto;

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
