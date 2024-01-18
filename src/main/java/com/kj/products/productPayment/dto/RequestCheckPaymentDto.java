package com.kj.products.productPayment.dto;

import lombok.Data;

@Data
// 프론트에서 받은 결제내역을 조회하기 위한 데이터들
public class RequestCheckPaymentDto {
    private String impUid;
    private int amount;
    private String userName;
}
