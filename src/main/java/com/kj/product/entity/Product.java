package com.kj.product.entity;

import com.kj.product.dto.ProductInputDto;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.productQnA.entity.ProductQnA;
import com.kj.productReview.entity.ProductReview;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    private String productNumber;

    private int productPrice;

    private String gender;

    private String productSeanson;

    private int clickCount;

    private LocalDateTime createdAt;

    private Long productDetailImageBucket;

    private String productDatailImage;

    // 작성자 수정한자 생성일 수정일

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "PRODUCT_IMAGE_ID")
    private ProductImage productImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "product",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductSize> productSize;

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductCart> productCart = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductLike> productLike = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductReview> productReview = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductQnA> productQnA = new ArrayList<>();

    @Builder
    public Product(ProductInputDto productInputDto, ProductImage productImage,ProductCategory productCategory) {
        this.productName = productInputDto.getProductName();
        this.productNumber = productInputDto.getProductNumber();
        this.productPrice = productInputDto.getProductPrice();
        this.gender = productInputDto.getGender();
        this.productSeanson = productInputDto.getProductSeason();
        this.productDetailImageBucket = productInputDto.getProductDetailImageBucket();
        this.productDatailImage = productInputDto.getProductDetailImage();
        this.productImages = productImage;
        this.productCategory = productCategory;

    }
}
