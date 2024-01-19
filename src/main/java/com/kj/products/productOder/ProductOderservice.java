package com.kj.products.productOder;

import com.kj.member.service.MemberService;
import com.kj.products.productOder.dto.ProductOrderSuccessDto;
import com.kj.products.productOder.entity.MyProductOrderDto;
import com.kj.products.productOder.repository.ProductOrderProductDetailRepository;
import com.kj.products.productOder.repository.ProductOrderInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductOderservice {
    private final MemberService memberService;
    private final ProductOrderInfoRepository productOrderRepository;
    private final ProductOrderProductDetailRepository productOrderDetailRepository;

    // 결제 후 바로 결제내역 보여주기
    public List<ProductOrderSuccessDto> findProductOrdersByOrderId(List<Long> orderIds) {
        return productOrderDetailRepository.findProductOrdersByOrderId(orderIds);

    }

    public List<MyProductOrderDto> findProductOrdersByUserNickName(String usernickname) {
        return productOrderDetailRepository.findProductOrdersByUserNickName(usernickname);

    }
}
