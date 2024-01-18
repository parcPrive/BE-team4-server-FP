package com.kj.products.productPayment.dto;

import lombok.Builder;
import lombok.Data;

@Data
// 아임포트에서 받을 reponse안에 들어있는 데이터를 뽑는 과정
public class ResponseGetData {
    private ResponseGetDto response;

    // response 안에 있는 accessToken
    public String getAccessToken(ResponseGetData response){
        String accessToken = response.getResponse().getAccess_token();
        return accessToken;
    }
    // response 안에 있는 결제체크에 필요한 데이터
    public ResponseGetPaymentDetail getPaymentDetail(ResponseGetData response){
        ResponseGetPaymentDetail getPaymentDetail = new ResponseGetPaymentDetail(response.getResponse());
        return getPaymentDetail;

    }
}
