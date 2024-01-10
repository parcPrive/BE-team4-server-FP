package com.kj.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.kj.product.entity.QProductImage.productImage;

public class ProductImageRepositoryImpl implements ProductImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public ProductImageRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);

    }

    @Override
    public void deleteProductImagebyProductId(int productId) {
        Long deleteProductImageCount =  queryFactory
                .delete(productImage)
                .where(productImage.product.id.eq((long) productId))
                .execute();
        if(deleteProductImageCount == 0){
            new RuntimeException("에러입니다.");
        }

    }
}
