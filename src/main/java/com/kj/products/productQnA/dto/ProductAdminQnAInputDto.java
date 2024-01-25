package com.kj.products.productQnA.dto;

import lombok.Data;

@Data
public class ProductAdminQnAInputDto {
    private Long productQnAId;
    private Long productId;
    private String adminId;
    private String productAdminQnAContent;
}
