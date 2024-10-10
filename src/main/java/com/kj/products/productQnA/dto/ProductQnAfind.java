package com.kj.products.productQnA.dto;

import com.kj.products.productQnA.entity.ProductQnA;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductQnAfind {
    private Long id;
    private String prodcutQACategory;
    private String productQATitle;
    private String productQAContent;
    private LocalDateTime createdAt;
    private String userName;
    private ProductQnA productQnA;

    public ProductQnAfind(ProductQnA productQnA) {
        this.id = productQnA.getId();
        this.prodcutQACategory = productQnA.getProductQnACategory().getProductQnACategoryName();
        this.productQATitle = productQnA.getProductQATitle();
        this.productQAContent = productQnA.getProductQAContent();
        this.createdAt = productQnA.getCreatedAt();
        this.userName = productQnA.getMember().getUserName();
        this.productQnA = productQnA.getChildren();
    }
}
