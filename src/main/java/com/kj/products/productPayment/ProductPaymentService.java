package com.kj.products.productPayment;

import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.product.repository.ProductSizeRepository;
import com.kj.products.productCart.entity.ProductCart;
import com.kj.products.productCart.repository.ProductCartRepository;
import com.kj.products.productOder.dto.ProductInsertOrderDto;
import com.kj.products.productOder.entity.ProductOrderInfo;
import com.kj.products.productOder.entity.ProductOrderProductDetail;
import com.kj.products.productOder.repository.ProductOrderInfoRepository;
import com.kj.products.productOder.repository.ProductOrderProductDetailRepository;
import com.kj.products.productPayment.dto.*;


import com.kj.products.productPayment.entity.ProductPayment;
import com.kj.products.productPayment.repository.ProductPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductPaymentService {
    private final PaymentFeignClient paymentFeignClient;
    private final ProductPaymentRepository productPaymentRepository;
    private final MemberService memberService;
    private final ProductOrderInfoRepository productOrderInfoRepository;
    private final ProductSizeRepository productSizeRepository;
    private final ProductCartRepository productCartRepository;
    private final ProductOrderProductDetailRepository productOrderProductDetailRepository;
    @Value("${import.restkey}")
    private String imp_key;
    @Value("${import.secretkey}")
    private String imp_secret;




    @Transactional
    public List<Long> insertProductPaymentDetailOrOrderInfo(ProductInsertOrderDto productInsertOrderDto,ProductPaymentInsertDto productPaymentInsertDto){
        Member findMember = memberService.findByUserNickName(productInsertOrderDto.getUserName());
        // 상품결제 내역 추가
        ProductPayment insertProductPaymentInfo = new ProductPayment(productPaymentInsertDto, findMember);
        ProductPayment insertResultProductPayment = productPaymentRepository.save(insertProductPaymentInfo);
        log.info("날짜?? ===>> {}", insertResultProductPayment.getCreatedAt());
        // 상품배송지와 배송한 사람?이름 저장
        ProductOrderInfo productOrderInfo = new ProductOrderInfo(productInsertOrderDto);
        ProductOrderInfo insertResultProductOrder = productOrderInfoRepository.save(productOrderInfo);
        // 사이즈 아이디로 주문상품조회해서 가격 가져오기 카트(장바구니)아이디로 주문 수량 가져오기
        log.info("사이즈들??? 머지 ===>> {}", productInsertOrderDto.getProductSizeId());
        List<ProductSize> productSizeAndProduct = productSizeRepository.findProductPriceByProductSizeId(productInsertOrderDto.getProductSizeId());
        List<ProductCart> productCartList = productCartRepository.findByProductCartId(productInsertOrderDto.getProductCartId());

        // 주문번호는 멀천트 아이디를 짤라서 오늘 날짜 붙혀 사용하기
        String[] merchantIdSplit = productInsertOrderDto.getMerchantId().split("_");
        String deliveryNumber = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + merchantIdSplit[1];
        // 오더상품에 저장하기 위해 리스트에 담아서 세이브한다.
        List<ProductOrderProductDetail> productOrderProductDetails = new ArrayList<>();
        for(int i = 0; i < productCartList.size(); i++){
            productOrderProductDetails.add(new ProductOrderProductDetail(deliveryNumber, productInsertOrderDto, productSizeAndProduct.get(i), productCartList.get(i),insertResultProductOrder,findMember,insertResultProductPayment));
            // 상품의 갯수를 구매 후 갯수고 바꾸는 작업 그리고 구매한 제품 장바구니에서 삭제
            productSizeAndProduct.get(i).setProductCount(productSizeAndProduct.get(i).getProductCount() - productCartList.get(i).getProductCount());
            productCartRepository.deleteById(productCartList.get(i).getId());
        }
        List<ProductOrderProductDetail> insertDatas = productOrderProductDetailRepository.saveAll(productOrderProductDetails);
        List<Long> productOrdersIds = new ArrayList<>();
        for(ProductOrderProductDetail productOrdersId : insertDatas){
            productOrdersIds.add(productOrdersId.getId());
        }
        return productOrdersIds;

    }



    // 결제 내역을 impUid로 아임포트 서버에 결제한 내역이 있느지 확인
    // 있다면 true 없다면 false를 반환한다.
    @Transactional
    public Map<String, Boolean> payment(RequestCheckPaymentDto paymentDto){
        String accessTokena = getToken();
        ResponseGetPaymentDetail paymentDetail = checkPayment(paymentDto.getImpUid(), accessTokena);
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

    // 결제 실패시 환불하는 로직
    public Boolean refund(String impUid, int productTotalPrice) {
        String accessToken = getToken();
        ResponseGetData refundData =  new ResponseGetData();
        ResponseGetRefundDetail resultRefundData =  refundData.getRefundDetail(paymentFeignClient.refund(impUid,productTotalPrice,accessToken));
        log.info("리펀드데이터 ===>>> {}", resultRefundData);
        if(!resultRefundData.getImp_uid().isEmpty()){
            return true;
        }
        return false;
    }

    //내 주문내역에서 환불요청하기
    @Transactional
    public ResponseGetRefundDetail myOrderRefund(Long productOrderId) {
        String accessToken = getToken();
        // productOrderProductDetail에서 ==>> impuid, 내가 찍은 상품의 가격 불러오기
        ProductOrderProductDetail findProductOrderInfo = productOrderProductDetailRepository.findProductPriceNimpUidByProductOrderId(productOrderId);
        log.info("전체!!!!!! ===>>> {}",findProductOrderInfo);
        log.info("getImpUid ===>>> {}",findProductOrderInfo.getProductPayment().getImpUid());
        log.info("getPrice ===>>> {}",findProductOrderInfo.getPrice());
        log.info("getcount ===>>> {}",findProductOrderInfo.getProductCount());
        log.info("단건 환불 토탈금액 ===>>> {}",findProductOrderInfo.getPrice() * findProductOrderInfo.getProductCount());
        ResponseGetData refundData =  new ResponseGetData();
        ResponseGetRefundDetail resultRefundData = refundData.getRefundDetail(paymentFeignClient.refund(findProductOrderInfo.getProductPayment().getImpUid(), findProductOrderInfo.getPrice() * findProductOrderInfo.getProductCount(), accessToken));
        log.info("환불 정보 ==>>>>{}", resultRefundData);

        // 받아온 환불정보가 있다면 환불정보를 추가해준다.
        // 사이즈에 있는 제품 수량을 환불한 수량만큼 추가해서 다시 업데이트해준다.
        // 그리고 주문내역에서 상품을 삭제한다.
        if(!resultRefundData.getImp_uid().isEmpty()){
            ProductPayment insertRefundInfo = new ProductPayment(resultRefundData,findProductOrderInfo.getMember());
            ProductPayment resultProductRefund = productPaymentRepository.save(insertRefundInfo);
            log.info("겟아이디  ===>>> {}",findProductOrderInfo.getProductSize().getId());
            ProductSize updataProductSize = productSizeRepository.findProductSizeByProductSizeId(findProductOrderInfo.getProductSize().getId());
            updataProductSize.setProductCount(updataProductSize.getProductCount() + findProductOrderInfo.getProductCount());
            // 상품주문정보에 있는 결제정보를 바꿔준다. 왜? 환불한거랑 결제한거랑 알기위해서 어떤 상품을 결제했고 환불했는지 알려고 productPayment id를 바꿔준다.
            Optional<ProductOrderProductDetail> findProductOrderDetail = productOrderProductDetailRepository.findById(findProductOrderInfo.getId());
            if(findProductOrderDetail.isPresent()){
                findProductOrderDetail.get().setProductPayment(resultProductRefund);
            }
        }
        return resultRefundData;


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
