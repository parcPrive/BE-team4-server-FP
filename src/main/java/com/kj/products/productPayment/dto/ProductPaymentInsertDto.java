package com.kj.products.productPayment.dto;

import lombok.Data;

@Data
public class ProductPaymentInsertDto {
    private String impUid;
    private int productTotalPrice;
    private String paymentStatus;
}
