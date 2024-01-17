package com.kj.products.productPayment;

import com.kj.config.PaymentFeignConfig;
import com.kj.products.productPayment.dto.PaymentGetTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="${feign.import.name}", url = "${feign.import.url}", configuration = PaymentFeignConfig.class)
public interface PaymentFeignClient {
    @PostMapping("/user/getToken")
    String getToken(@RequestBody PaymentGetTokenDto paymentGetTokenDto);
}
