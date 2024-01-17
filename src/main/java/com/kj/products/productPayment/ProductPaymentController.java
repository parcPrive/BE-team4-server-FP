package com.kj.products.productPayment;

import com.kj.products.productPayment.dto.PaymentGetTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/productpayment")
public class ProductPaymentController {
    private final PaymentFeignClient paymentFeignClient;
    private final PaymentGetTokenDto paymentGetTokenDto;
    @GetMapping("/getToken")
    @ResponseBody
    public String ProductPaymentGetToken(){

        paymentGetTokenDto.key();
        log.info("페이먼트디티오 ==>>>{}",paymentGetTokenDto);
        String aaa = paymentFeignClient.getToken(paymentGetTokenDto);
        log.info("여기에는?? ===>> {}", aaa);
        return "ads";
    }
}
