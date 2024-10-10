package com.kj.products.productPayment;


import com.kj.products.productOder.dto.ProductInsertOrderDto;
import com.kj.products.productPayment.dto.ProductPaymentInsertDto;
import com.kj.products.productPayment.dto.RequestCheckPaymentDto;
import com.kj.products.productPayment.dto.ResponseGetPaymentDetail;
import com.kj.products.productPayment.dto.ResponseGetRefundDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/productpayment")
public class ProductPaymentController {
    private final ProductPaymentService productPaymentService;

    // ================결제 정보 저장=============
    @PostMapping("/insert")
    @ResponseBody
    public List<Long> insertPaymentInfo(
            @ModelAttribute ProductInsertOrderDto productInsertOrderDto,
            @ModelAttribute ProductPaymentInsertDto productPaymentInsertDto
            ){
        log.info("인설트 인포디티오 ===>>> {}", productInsertOrderDto);
        log.info("인설트 페이먼트 디티오 ===>>> {}", productPaymentInsertDto);
        return productPaymentService.insertProductPaymentDetailOrOrderInfo(productInsertOrderDto,productPaymentInsertDto);
//        return "/product/paymentsuccess";
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

    @GetMapping("/refund")
    @ResponseBody
    public Boolean ProuctRefund(
            @RequestParam String impUid,
            @RequestParam int productTotalPrice
    ){
        log.info("impUid ===>> {}", impUid);
        log.info("productTotalPrice ===>>> {}", productTotalPrice);
        return productPaymentService.refund(impUid, productTotalPrice);
    }
    // 나의 주문내역에서 환불요청
    @GetMapping("/myorderrefund")
    @ResponseBody
    public ResponseGetRefundDetail productMyOrderRefund(
            @RequestParam Long productOrderId
    ){
        log.info("productOrderId ====>>> {}",productOrderId);
        return productPaymentService.myOrderRefund(productOrderId);
//        return "asdasd";
    }
}
