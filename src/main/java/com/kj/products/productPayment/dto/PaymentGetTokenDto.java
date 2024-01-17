package com.kj.products.productPayment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Service
public class PaymentGetTokenDto {
    @Value("${import.restkey}")
    private String restKey;
    @Value("${import.secretkey}")
    private String secretKey;

    public void key(){
        log.info("1111 ===>>> {}",restKey);
        log.info("2222 ===>>> {}", secretKey);
    }
}
