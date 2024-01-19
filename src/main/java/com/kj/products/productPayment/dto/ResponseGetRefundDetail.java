package com.kj.products.productPayment.dto;

import lombok.Data;

@Data
public class ResponseGetRefundDetail {
    private String buyer_name;
    private String imp_uid;
    private int cancel_amount;
    private String status;

    public ResponseGetRefundDetail(ResponseGetDto response) {
        this.buyer_name = response.getBuyer_name();
        this.imp_uid = response.getImp_uid();
        this.cancel_amount = response.getCancel_amount();
        this.status = response.getStatus();
    }
}
