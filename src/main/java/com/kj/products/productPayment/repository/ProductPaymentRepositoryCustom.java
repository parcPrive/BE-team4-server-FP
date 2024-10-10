package com.kj.products.productPayment.repository;

public interface ProductPaymentRepositoryCustom {
    Integer sumProductPaidPriceByUserNickName(String userNickName);
    Integer sumProductRefundPriceByUserNickName(String userNickName);
}
