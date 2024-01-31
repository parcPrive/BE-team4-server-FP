package com.kj.products.productPayment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.kj.products.productPayment.entity.QProductPayment.productPayment;

public class ProductPaymentRepositoryImpl implements ProductPaymentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductPaymentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 총 결제금액
    @Override
    public Integer sumProductPaidPriceByUserNickName(String userNickName) {
        Integer productPaidPridceSum = queryFactory.select(productPayment.productTotalPrice.sum())
                .from(productPayment)
                .where(productPayment.member.nickName.eq(userNickName),
                        productPayment.status.eq("paid"))
                .fetchOne();
        return productPaidPridceSum;
    }
    // 총 환불금액
    @Override
    public Integer sumProductRefundPriceByUserNickName(String userNickName) {
        Integer productRefundPriceSum = queryFactory.select(productPayment.productTotalPrice.sum())
                .from(productPayment)
                .where(productPayment.member.nickName.eq(userNickName),
                        productPayment.status.eq("cancelled"))
                .fetchOne();
        return productRefundPriceSum;
    }
}
