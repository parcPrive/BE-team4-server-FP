package com.kj.products.productOder.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class MyProductOrderDto {
    private Long productOrderId;
    private String productImage;
    private String productName;
    private String productSize;
    private LocalDateTime orderDate;
    private String deliveryNumber;
    private int productPrice;
    private int productCount;

    @Builder
    public MyProductOrderDto(ProductOrderProductDetail findMyOrder) {
        this.productOrderId = findMyOrder.getId();
        this.productImage = findMyOrder.getProductSize().getProduct().getProductImages().get(0).getImageName();
        this.productName = findMyOrder.getProductSize().getProduct().getProductName();
        this.productSize = findMyOrder.getProductSize().getProductSize();
        this.orderDate = findMyOrder.getProductPayment().getCreatedAt();
        this.deliveryNumber = findMyOrder.getDeliveryNumber();
        this.productPrice = findMyOrder.getPrice();
        this.productCount = findMyOrder.getProductCount();
    }
}
