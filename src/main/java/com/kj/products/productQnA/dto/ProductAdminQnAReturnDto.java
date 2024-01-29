package com.kj.products.productQnA.dto;

import com.kj.products.productQnA.entity.ProductQnA;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProductAdminQnAReturnDto {
    private Long id;
    private String adminId;
    private String productAdminQnAContent;
    private LocalDateTime createdAt;


    public ProductAdminQnAReturnDto(ProductQnA productQnA) {
        this.id = productQnA.getId();
        this.adminId = productQnA.getMember().getNickName();
        this.productAdminQnAContent = productQnA.getProductQAContent();
        this.createdAt = productQnA.getCreatedAt();
    }
}
