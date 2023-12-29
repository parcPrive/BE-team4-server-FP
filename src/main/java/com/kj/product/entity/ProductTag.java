package com.kj.product.entity;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
public class ProductTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productTag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder
    public ProductTag(String productTag, Product product) {
        this.productTag = productTag;
        this.product = product;
    }
}
