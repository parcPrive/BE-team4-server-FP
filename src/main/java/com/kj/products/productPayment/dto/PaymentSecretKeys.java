package com.kj.products.productPayment.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Data
@Slf4j
@Service
// 아임포트에서 발급 받은 시크릿키
public class PaymentSecretKeys {
    private String imp_key;
    private String imp_secret;
    public PaymentSecretKeys(String imp_key, String imp_secret) {
        this.imp_key = imp_key;
        this.imp_secret = imp_secret;
    }

}
