package com.kj.products.productPayment.dto;

import lombok.Data;

@Data
// 아임포트에서 준 response 안에 있는 데이터를 다 담아서 필요한거 뽑기위해 만들어준다 나중에 필요한게
// 있으면 계속 추가
public class ResponseGetDto {
    private String access_token;
    private int amount;
    private String buyer_name;
    private String imp_uid;
}
