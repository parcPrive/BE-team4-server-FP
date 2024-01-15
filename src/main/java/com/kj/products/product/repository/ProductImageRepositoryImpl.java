package com.kj.products.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.kj.products.product.entity.QProductImage.*;

public class ProductImageRepositoryImpl implements ProductImageRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public ProductImageRepositoryImpl(EntityManager em) {
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
