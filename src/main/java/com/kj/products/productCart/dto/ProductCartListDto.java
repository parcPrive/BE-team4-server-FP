package com.kj.products.productCart.dto;

import com.kj.products.product.entity.ProductSize;
import com.kj.products.productCart.entity.ProductCart;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductCartListDto {

    private Long productId;
    private Long productCartId;
    private String productName;
    private ProductSize productSize;
    private int productCount;
    private int productPrice;
    private String productImage;

    @Builder
    public ProductCartListDto(ProductCart findProductCartList) {
        this.productId = findProductCartList.getProductSize().getProduct().getId();
        this.productCartId = findProductCartList.getId();
        this.productName = findProductCartList.getProductSize().getProduct().getProductName();
        this.productSize = findProductCartList.getProductSize();
        this.productCount = findProductCartList.getProductCount();
        this.productPrice = findProductCartList.getProductSize().getProduct().getProductPrice();
        this.productImage = findProductCartList.getProductSize().getProduct().getProductImages().get(0).getImageName();
    }


}
