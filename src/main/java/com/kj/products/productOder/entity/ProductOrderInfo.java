package com.kj.products.productOder.entity;

import com.kj.products.productOder.dto.ProductInsertOrderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ORDER_ID")
    private Long id;

    private String deliveryAddress;

    private String deliveryDetailAddress;

    private String userName;

    private String deliveryRequest;

    @Builder
    public ProductOrderInfo(ProductInsertOrderDto productInsertOrderDto) {
        this.deliveryAddress = productInsertOrderDto.getUserAddress();
        this.deliveryDetailAddress = productInsertOrderDto.getUserPostCode();
        this.userName = productInsertOrderDto.getUserName();
        this.deliveryRequest = productInsertOrderDto.getDeliveryRequest();
    }
}
