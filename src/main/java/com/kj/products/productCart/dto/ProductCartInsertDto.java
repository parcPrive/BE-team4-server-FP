package com.kj.products.productCart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ProductCartInsertDto {
    private Long productSizeId;
    private int productPrice;
    private int productTotalCount;
    private String userId;
}
