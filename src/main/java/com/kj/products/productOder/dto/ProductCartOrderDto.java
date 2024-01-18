package com.kj.products.productOder.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductCartOrderDto {
    private String userId;
    private List<Long> productCartId;
}
