package com.kj.products.productReview.entity;

import com.kj.member.entity.Member;
import com.kj.products.product.entity.Product;
import com.kj.products.productReview.dto.ProductinsertReviewDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    @Id
    @GeneratedValue
    private Long id;

    private String userName; //유저이름으로ㅓ..

    private String productReview;

    @Column(length = 400)
    private List<String> productImage = new ArrayList<>();

    private int star;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public ProductReview(ProductinsertReviewDto productinsertReviewDto,List<String> productReviewImage , Product product, Member member) {
        this.userName = productinsertReviewDto.getUserName();
        this.productReview = productinsertReviewDto.getProductReview();
        this.productImage = productReviewImage;
        this.star = productinsertReviewDto.getStar();
        this.createdAt = LocalDateTime.now();
        this.product = product;
        this.member = member;
    }
}
