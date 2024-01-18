package com.kj.products.productPayment;


import com.kj.products.productOder.dto.ProductInsertOrderDto;
import com.kj.products.productPayment.dto.ProductPaymentInsertDto;
import com.kj.products.productPayment.dto.RequestCheckPaymentDto;
import com.kj.products.productPayment.dto.ResponseGetPaymentDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/productpayment")
public class ProductPaymentController {
    private final ProductPaymentService productPaymentService;

    // ================결제 정보 저장=============
    @PostMapping("/insert")
    public void insertPaymentInfo(
            @ModelAttribute ProductInsertOrderDto productInsertOrderDto,
            @ModelAttribute ProductPaymentInsertDto productPaymentInsertDto
            ){
        log.info("인설트 인포디티오 ===>>> {}", productInsertOrderDto);
        log.info("인설트 페이먼트 디티오 ===>>> {}", productPaymentInsertDto);
        productPaymentService.insertProductPaymentDetailOrOrderInfo(productInsertOrderDto,productPaymentInsertDto);

    }




    // http://localhost:8090/productpayment/getToken
    @GetMapping("/getToken")
    @ResponseBody
    public String ProductPaymentGetToken(){
        String accessToken =  productPaymentService.getToken();
        return accessToken;
    }

    @GetMapping("/checkpayment")
    @ResponseBody
    public Map<String, Boolean> ProductCheckPayment(
            @ModelAttribute RequestCheckPaymentDto paymentDto
    ){
        // http://localhost:8090/productpayment/checkpayment
        log.info("여기에?? ===>> {}", paymentDto);
        Map<String, Boolean> isPaymentDetail = productPaymentService.payment(paymentDto);
        log.info("======>>>>>{}",isPaymentDetail.get("result"));
        return isPaymentDetail;
    }
}
