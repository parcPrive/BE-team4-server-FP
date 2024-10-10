package com.kj.products.productCart;

import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.products.product.entity.ProductSize;
import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productCart.repository.ProductCartRepository;
import com.kj.products.product.repository.ProductSizeRepository;
import com.kj.products.productCart.dto.ProductCartInsertDto;
import com.kj.products.productCart.entity.ProductCart;
import com.kj.products.productOder.dto.ProductCartOrderDto;
import com.kj.products.productOder.dto.ProductOrderInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductCartService {
    private final ProductCartRepository productCartRepository;
    private final ProductSizeRepository productSizeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long insertProductCart(ProductCartInsertDto productCartInsertDto) {
        Optional<ProductSize> findProductSize = productSizeRepository.findById(productCartInsertDto.getProductSizeId());
        Optional<Member> findMember = memberRepository.findByUserId(productCartInsertDto.getUserId());
        if(findMember.isPresent() && findProductSize.isPresent()){
            log.info(findMember.get().getUserId());
            log.info(findProductSize.get().getProductSize());
            if(findProductSize.get().getProductCount() > productCartInsertDto.getProductTotalCount()){
                ProductCart insertProductCart = new ProductCart(productCartInsertDto, findMember.get(), findProductSize.get());
                productCartRepository.save(insertProductCart);
            }
        }
        return productCartRepository.countByUserId(productCartInsertDto.getUserId());

    }

    public List<ProductCartListDto> findByUserId(String userId) {
        List<ProductCartListDto> productCartList = productCartRepository.findByUserId(userId);
        return productCartList;


    }

    @Transactional
    public void deleteByProductCartId(Long productCartId) {
        productCartRepository.deleteById(productCartId);
    }

    public List<ProductOrderInfoDto> findByUserNickInProductCartId(ProductCartOrderDto productCartOrderDto) {
        return productCartRepository.findByUserNickInProductCartId(productCartOrderDto);
    }
}

