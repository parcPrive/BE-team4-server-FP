package com.kj.products.productPayment;

import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import com.kj.products.productOder.dto.ProductInsertOrderDto;
import com.kj.products.productPayment.dto.*;


import com.kj.products.productPayment.repository.ProductPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductPaymentService {
    private final PaymentFeignClient paymentFeignClient;
    private final ProductPaymentRepository productPaymentRepository;
    private final MemberService memberService;
    @Value("${import.restkey}")
    private String imp_key;
    @Value("${import.secretkey}")
    private String imp_secret;


    public void insertProductPaymentDetailOrOrderInfo(ProductInsertOrderDto productInsertOrderDto,ProductPaymentInsertDto productPaymentInsertDto){
        Member findMember = memberService.findByUserNickName(productInsertOrderDto.getUserName());

//        productPaymentRepository.save()
    }



    // 결제 내역을 impUid로 아임포트 서버에 결제한 내역이 있느지 확인
    // 있다면 true 없다면 false를 반환한다.
    @Transactional
    public Map<String, Boolean> payment(RequestCheckPaymentDto paymentDto){
        String accessToken = getToken();
        ResponseGetPaymentDetail paymentDetail = checkPayment(paymentDto.getImpUid(), accessToken);
        Map<String, Boolean> isPaymentDetail = new HashMap<>();
        if(paymentDetail == null) {
            log.info("문제있다.");
            isPaymentDetail.put("result", false);
            return isPaymentDetail;
        }else if(paymentDetail.getAmount() == paymentDto.getAmount() &&
                paymentDetail.getBuyer_name().equals(paymentDto.getUserName()) &&
                paymentDetail.getImp_uid().equals(paymentDto.getImpUid())){
            log.info("문제없다");
            isPaymentDetail.put("result", true);
            return isPaymentDetail;
        }
        isPaymentDetail.put("result", false);
        return isPaymentDetail;
    }

    // 아임포트에서 토큰을 발급받는다.
    // 아임포트에서 받은 시크릿키로
    public String getToken(){
        PaymentSecretKeys paymentGetTokenDto = new PaymentSecretKeys(imp_key,imp_secret);
        log.info("디티오 ===>> {}", paymentGetTokenDto);
        ResponseGetData getToken = new ResponseGetData();
        String accessToken = getToken.getAccessToken(paymentFeignClient.getToken(paymentGetTokenDto));
        log.info("엑세스 토큰 ===>>> {}",accessToken);
         return accessToken;

//        log.info("aa ==>> {} ", paymentFeignClient.getToken(paymentGetTokenDto));

    }
    // 결제 내역이 있느지 체크한다.
    public ResponseGetPaymentDetail checkPayment(String impUid, String accessToken){
        String headerAccessToken = "Bearer " + accessToken;
        ResponseGetData getPaymentDetail = new ResponseGetData();
        ResponseGetPaymentDetail paymentDetail =  getPaymentDetail.getPaymentDetail(paymentFeignClient.checkPayment(impUid, headerAccessToken));
        log.info("페이먼트 디테일 ==>> {}", paymentDetail);
        return paymentDetail;
    }

}
