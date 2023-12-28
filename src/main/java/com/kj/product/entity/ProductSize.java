package com.kj.product.entity;

import com.kj.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_SIZE_ID")
    private Long id;

    private String productSize; //이건 enum타입으로 관리
    private int productCount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Builder
    public ProductSize(String productSize, int productCount, Product product) {
        this.productSize = productSize;
        this.productCount = productCount;
        this.product = product;
    }
}
