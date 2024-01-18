package com.kj.products.productPayment;

import com.kj.config.PaymentFeignConfig;
import com.kj.products.productPayment.dto.PaymentSecretKeys;
import com.kj.products.productPayment.dto.ResponseGetData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="${feign.import.name}", url = "${feign.import.url}", configuration = PaymentFeignConfig.class, dismiss404 = true)
public interface PaymentFeignClient {
    @PostMapping("/users/getToken")
    ResponseGetData getToken(PaymentSecretKeys paymentGetTokenDto);

    @GetMapping("/payments/{imp_uid}")
    ResponseGetData checkPayment(
            @PathVariable(value = "imp_uid") String imp_uid,
            @RequestHeader("Authorization") String headerAccessToken);
}
