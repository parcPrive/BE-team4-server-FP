package com.kj.products.productPayment.dto;

import lombok.Data;

@Data
// 아임포트에서 impUid로 조회한 결제 내역에서 필요한 값을 뽑아온것
public class ResponseGetPaymentDetail {
    private int amount;
    private String buyer_name;
    private String imp_uid;


    public ResponseGetPaymentDetail(ResponseGetDto response) {
        this.amount = response.getAmount();
        this.buyer_name = response.getBuyer_name();
        this.imp_uid = response.getImp_uid();
    }

}
