package com.kj.products.productQnA.dto;

import lombok.Data;

@Data
public class ProductQnAInputDto {
    private Long productQACategoryId;
    private String productQATitle;
    private String ProductQAContent;
    private Long productId;
    private String userName;
}
