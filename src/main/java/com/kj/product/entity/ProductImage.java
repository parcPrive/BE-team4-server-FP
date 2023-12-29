package com.kj.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.EnableMBeanExport;

@Entity
@Getter
@NoArgsConstructor
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;

    private int thubmnail;

    private String bucketName;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;


    @Builder
    public ProductImage(String imageName, int thubmnail, String bucketName ,Product product) {
        this.imageName = imageName;
        this.thubmnail = thubmnail;
        this.bucketName = bucketName;
        this.product = product;
    }

}
