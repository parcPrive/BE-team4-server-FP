package com.kj.products.productOder.repository;

import com.kj.products.productOder.dto.ProductOrderSuccessDto;
import com.kj.products.productOder.entity.MyProductOrderDto;
import com.kj.products.productOder.entity.ProductOrderProductDetail;

import java.util.List;

public interface ProductOrderProductDetailRepositoryCustom {
    List<ProductOrderSuccessDto> findProductOrdersByOrderId(List<Long> orderIds);
    List<MyProductOrderDto> findProductOrdersByUserNickName(String userNickName);
    ProductOrderProductDetail findProductPriceNimpUidByProductOrderId(Long productOrderId);
}
