package com.kj.products.product.dto;

import com.kj.products.product.entity.Product;
import com.kj.products.product.entity.ProductImage;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.product.entity.ProductTag;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductUpdateDto {
    private Long id;
    private String productName;
    private String productNumber;
    private int productPrice;
    private String gender;
    private String productSeason;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime updateedAt;
    // ckediter에서 들어오니까
    private String productDetailImageBucket;
    private String productDetailImage;
    private ProductCategoryUpdateDto productCategory;
    // 사이즈와 수량
    private List<ProductSize> productSize;
    // 상품 이미지
    private List<ProductImage> productImages;
    private List<ProductTag> productTags;
//    private ProductCategoryUpdateDto productCategory;
//    // 사이즈와 수량
//    private List<ProductSizeUpdateDto> productSize;
//    // 상품 이미지
//    private List<ProductImageUpdateDto> productImages;
//    private List<ProductTagUpdateDto> productTags;

    @Builder
    public ProductUpdateDto(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.productNumber = product.getProductNumber();
        this.productPrice = product.getProductPrice();
        this.gender = product.getGender();
        this.productSeason = product.getProductSeason();
        this.writer = product.getWriter();
        this.createdAt = product.getCreatedAt();
        this.updateedAt = product.getUpdateedAt();
        this.productDetailImageBucket = product.getProductDetailImageBucket();
        this.productDetailImage = product.getProductDatailImage();
        this.productCategory = new ProductCategoryUpdateDto(product.getProductCategory());
        this.productSize = product.getProductSize();
        this.productImages = product.getProductImages();
        this.productTags = product.getProductTags();
    }

//    @QueryProjection
//    public ProductUpdateDto(Long id, String productName, String productNumber, int productPrice, String gender, String productSeason, String writer, LocalDateTime createdAt, LocalDateTime updateedAt, Long productDetailImageBucket, String productDetailImage, ProductCategoryUpdateDto productCategory, Set<ProductSizeUpdateDto> productSize, Set<ProductImageUpdateDto> productImages, Set<ProductTagUpdateDto> productTags) {
//        this.id = id;
//        this.productName = productName;
//        this.productNumber = productNumber;
//        this.productPrice = productPrice;
//        this.gender = gender;
//        this.productSeason = productSeason;
//        this.writer = writer;
//        this.createdAt = createdAt;
//        this.updateedAt = updateedAt;
//        this.productDetailImageBucket = productDetailImageBucket;
//        this.productDetailImage = productDetailImage;
//        this.productCategory = productCategory;
//        this.productSize = productSize;
//        this.productImages = productImages;
//        this.productTags = productTags;
//    }



}
