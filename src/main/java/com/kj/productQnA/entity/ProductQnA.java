package com.kj.productQnA.entity;

import com.kj.products.product.entity.Product;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProductQnA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productQAKind;
    private String productQATitle;
    private String productQAContent;
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


}
