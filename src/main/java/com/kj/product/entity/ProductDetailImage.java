package com.kj.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ProductDetailImage {
    @Id
    private String id;
    private String productDatailImage;
}
