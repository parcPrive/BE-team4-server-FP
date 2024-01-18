package com.kj.products.productOder.dto;

import lombok.Data;

import java.util.List;
@Data
public class ProductInsertOrderDto {
    private List<Long> productCartId;
    private List<Long> productSizeId;
    private String merchantId;

    private String userName;
    private String userAddress;
    private String userPostCode;
    private String userPhoneNum;
    private String deliveryRequest;
}
