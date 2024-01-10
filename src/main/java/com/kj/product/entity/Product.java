package com.kj.product.entity;

import com.kj.product.dto.ProductInputDto;
import com.kj.product.dto.ProductUpdateInputDto;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.productQnA.entity.ProductQnA;
import com.kj.productReview.entity.ProductReview;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String productName;

    private String productNumber;

    private int productPrice;

    private String gender;

    private String productSeason;

    private int clickCount;

    private LocalDateTime createdAt;

    private String productDetailImageBucket;

    private String productDatailImage;


    // 작성자 수정한자 생성일 수정일
    private String writer;
    private LocalDateTime updateedAt;

    // 상풍등록 할때 같이 등록해야하는것들
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductImage> productImages;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<ProductSize> productSize;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.REMOVE)
    private List<ProductTag> productTags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory productCategory;

    // 상품 등록 이후
    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductCart> productCart = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductLike> productLike = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductReview> productReview = new ArrayList<>();

    @OneToMany(mappedBy = "product" ,fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ProductQnA> productQnA = new ArrayList<>();



    @Builder
    public Product(ProductInputDto productInputDto,ProductCategory productCategory) {
        this.productName = productInputDto.getProductName();
        this.productNumber = productInputDto.getProductNumber();
        this.productPrice = productInputDto.getProductPrice();
        this.gender = productInputDto.getGender();
        this.productSeason = productInputDto.getProductSeason();
        this.productDetailImageBucket = productInputDto.getProductDetailImageBucket();
        this.productDatailImage = productInputDto.getProductDetailImage();
        this.productCategory = productCategory;
        this.clickCount = 0;
        this.createdAt = LocalDateTime.now();
        this.writer = productInputDto.getWriter();
    }

    public void setProduct(ProductUpdateInputDto productUpdateInputDto, ProductCategory productCategory){
        this.productName = productUpdateInputDto.getProductName();
        this.productNumber = productUpdateInputDto.getProductNumber();
        this.productPrice = productUpdateInputDto.getProductPrice();
        this.gender = productUpdateInputDto.getGender();
        this.productSeason = productUpdateInputDto.getProductSeason();
        this.productDetailImageBucket = productUpdateInputDto.getProductDetailImageBucket();
        this.productDatailImage = productUpdateInputDto.getProductDetailImage();
        this.productCategory = productCategory;
        this.updateedAt = LocalDateTime.now();
        this.writer = productUpdateInputDto.getWriter();

    }

    public void setProductLike(List<ProductLike> productLike) {
        this.productLike = productLike;
    }
}
