package com.kj.product.dto;

public record ProductInputDto(
        String productName,
        String productNumber,
        String productPrice,
        String gender,
        String productSeason,
        String productSize,
        int productCount,
        String productDetailImage
) {}
