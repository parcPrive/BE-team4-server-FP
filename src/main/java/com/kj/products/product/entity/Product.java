package com.kj.products.product.entity;

import com.kj.products.product.dto.ProductInputDto;
import com.kj.products.product.dto.ProductUpdateInputDto;
import com.kj.productCategory.entity.ProductCategory;
import com.kj.products.productQnA.entity.ProductQnA;
import com.kj.products.productReview.entity.ProductReview;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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

    private String productSeason;

    private int clickCount;

    private LocalDateTime createdAt;

    private String productDetailImageBucket;

    private String productDatailImage;


    // 작성자 수정한자 생성일 수정일
    private String writer;
    private LocalDateTime updateedAt;

    // 상풍등록 할때 같이 등록해야하는것들
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProductImage> productImages = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProductSize> productSize =  new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "product",  cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<ProductTag> productTags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_CATEGORY_ID")
    private ProductCategory productCategory;

    // 상품 등록 이후
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

    public void setClickCount(int productClickcount){
        this.clickCount = productClickcount;
    }

    public void testProduct(String productName, String productNumber, int productPrice, String gender, String productSeason, String productDetailImageBucket, String productDatailImage){
        this.productName = productName;
        this.productNumber = productNumber;
        this.productPrice = productPrice;
        this.gender = gender;
        this.productSeason = productSeason;
        this.productDetailImageBucket = productDetailImageBucket;
        this.productDatailImage = productDatailImage;
    }

}
