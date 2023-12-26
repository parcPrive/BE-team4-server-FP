package com.kj.product.entity;

import com.kj.productCategory.entity.ProductCategory;
import com.kj.productQnA.entity.ProductQnA;
import com.kj.productReview.entity.ProductReview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String productName;

    private int productNumber;

    private int price;

    private String gender;

    private String seanson;

    private int clickCount;

    private LocalDateTime createdAt;

    // 작성자 수정한자 생성일 수정일

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PRODUCT_IMAGE_ID")
    private ProductImage productImages;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PRODCUT_DETAIL_IMAGE_ID")
    private ProductDetailImage productDetailImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductSize> productSize = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductCart> productCart = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductLike> productLike = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductReview> productReview = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductQnA> productQnA = new ArrayList<>();



}
