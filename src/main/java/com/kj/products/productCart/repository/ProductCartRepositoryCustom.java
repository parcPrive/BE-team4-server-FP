package com.kj.products.productCart.repository;

import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productCart.entity.ProductCart;
import com.kj.products.productOder.dto.ProductCartOrderDto;
import com.kj.products.productOder.dto.ProductOrderInfoDto;

import java.util.List;

public interface ProductCartRepositoryCustom {
    Long countByUserId(String userId);
    List<ProductCartListDto> findByUserId(String userId);

    List<ProductCart> findByProductCartId(List<Long> productCartId);
    List<ProductOrderInfoDto> findByUserNickInProductCartId(ProductCartOrderDto productCartOrderDto);
}
