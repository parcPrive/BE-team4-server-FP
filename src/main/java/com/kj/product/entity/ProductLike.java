package com.kj.product.entity;

import com.kj.product.entity.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProductLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
