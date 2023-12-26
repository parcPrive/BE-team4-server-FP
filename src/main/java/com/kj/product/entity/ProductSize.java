package com.kj.product.entity;

import com.kj.product.entity.Product;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productSize; //이건 enum타입으로 관리
    private int productCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
}
