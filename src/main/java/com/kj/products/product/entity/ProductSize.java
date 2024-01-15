package com.kj.products.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "productSize", "productCount"})
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

    public ProductSize(ProductSize productSize){
        this.id = productSize.getId();
        this.productSize = productSize.getProductSize();
        this.productCount = productSize.productCount;
        this.product = productSize.getProduct();
    }
}
