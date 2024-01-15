package com.kj.products.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "imageName", "thubmnail", "bucketName"})
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    private String imageName;

    @Column(length = 500)
    private int thubmnail;

    private String bucketName;

//    private List<image> images;

    @ManyToOne(fetch = FetchType.LAZY)
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

