package com.kj.products.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.kj.products.product.entity.QProductTag.*;


public class ProductTagRepositoryImpl implements ProductTagRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductTagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public void deleteProductTagbyProductId(Long productId) {
        Long deleteTagCount = queryFactory
                .delete(productTag1)
                .where(productTag1.product.id.eq(productId))
                .execute();
        if(deleteTagCount == 0){
            new RuntimeException("태그가 없습니다.");
        }
    }
}
