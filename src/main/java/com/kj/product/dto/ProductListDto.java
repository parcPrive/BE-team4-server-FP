package com.kj.product.dto;

import com.kj.product.entity.Product;
import com.kj.product.entity.ProductImage;
import com.kj.product.entity.ProductTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

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
