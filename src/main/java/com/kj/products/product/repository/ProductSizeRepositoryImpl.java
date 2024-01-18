package com.kj.products.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.kj.products.product.entity.QProductSize.productSize1;

public class ProductSizeRepositoryImpl implements ProductSizeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductSizeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public void deleteProductSizeByProductId(Long productId) {
            Long deleteProductImageCount =  queryFactory
                    .delete(productSize1)
                    .where(productSize1.product.id.eq(productId))
                    .execute();
            if(deleteProductImageCount == 0){
                new RuntimeException("에러입니다.");
            }
        }

}
