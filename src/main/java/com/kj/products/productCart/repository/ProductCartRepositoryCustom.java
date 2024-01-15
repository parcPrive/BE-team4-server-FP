package com.kj.products.productCart.repository;

import com.kj.products.productCart.dto.ProductCartListDto;
import com.kj.products.productCart.entity.ProductCart;

import java.util.List;

public interface ProductCartRepositoryCustom {
    Long countByUserId(String userId);
    List<ProductCartListDto> findByUserId(String userId);
}
