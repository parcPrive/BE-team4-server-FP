package com.kj.products.productOder.dto;

import com.kj.products.productOder.entity.ProductOrderProductDetail;
import lombok.Data;

@Data
public class ProductOrderSuccessDto {
    private String productName;
    private int productCount;
    private int productPrice;
    private String productSize;
    private String productImage;
    private String deliveryNumber;
    private String deliveryPostCode;
    private String deliveryAddress;
    private String userName;

    public ProductOrderSuccessDto(ProductOrderProductDetail findProductOrder) {
        this.productName = findProductOrder.getProductSize().getProduct().getProductName();
        this.productCount = findProductOrder.getProductCount();
        this.productPrice = findProductOrder.getPrice();
        this.productSize = findProductOrder.getProductSize().getProductSize();
        this.productImage = findProductOrder.getProductSize().getProduct().getProductImages().get(0).getImageName();
        this.deliveryNumber = findProductOrder.getDeliveryNumber();
        this.deliveryPostCode = findProductOrder.getProductOrder().getDeliveryDetailAddress();
        this.deliveryAddress = findProductOrder.getProductOrder().getDeliveryAddress();
        this.userName = findProductOrder.getProductOrder().getUserName();
    }
}
