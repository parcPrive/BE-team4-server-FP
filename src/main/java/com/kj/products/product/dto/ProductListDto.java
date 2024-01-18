package com.kj.products.product.dto;

import com.kj.products.product.entity.Product;
import com.kj.products.product.entity.ProductImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ProductListDto {
    private Long id;
    private String ProductName;
    private List<ProductImage> productImages;
    private int productPrice;

    @Builder
    public ProductListDto(Product findListProducts) {
        this.id = findListProducts.getId();
        this.ProductName = findListProducts.getProductName();
        this.productPrice = findListProducts.getProductPrice();
        this.productImages = findListProducts.getProductImages();
    }

    @QueryProjection
    public ProductListDto(Long id, String productName, List<ProductImage> productImages, int productPrice) {
        this.id = id;
        this.ProductName = productName;
        this.productImages = productImages;
        this.productPrice = productPrice;
    }
}
