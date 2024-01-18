package com.kj.productReview.entity;

import com.kj.products.product.entity.Product;
import jakarta.persistence.*;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String productReview;

    private String productImage;

    private int star;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
