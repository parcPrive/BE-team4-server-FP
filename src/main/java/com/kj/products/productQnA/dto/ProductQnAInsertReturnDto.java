package com.kj.products.productQnA.dto;

import com.kj.products.productQnA.entity.ProductQnA;
import com.kj.products.productQnACategory.entity.ProductQnACategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProductQnAInsertReturnDto {
    private Long id;
    private String productQATitle;
    private String productQAContent;
    private LocalDateTime createdAt;
    private String productQnACategoryName;
    private String userName;

    @Builder
    public ProductQnAInsertReturnDto(ProductQnA productQnA) {
        this.productQATitle = productQnA.getProductQATitle();
        this.productQAContent = productQnA.getProductQAContent();
        this.createdAt = productQnA.getCreatedAt();
        this.productQnACategoryName = productQnA.getProductQnACategory().getProductQnACategoryName();
        this.userName = productQnA.getMember().getNickName();
    }
}
