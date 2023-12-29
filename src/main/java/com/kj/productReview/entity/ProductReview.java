package com.kj.productReview.entity;

import com.kj.product.entity.Product;
import jakarta.persistence.*;

import java.util.List;

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
