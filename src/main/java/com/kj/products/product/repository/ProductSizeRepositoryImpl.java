package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductSize;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.kj.products.product.entity.QProduct.*;
import static com.kj.products.product.entity.QProductSize.productSize1;

public class ProductSizeRepositoryImpl implements ProductSizeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductSizeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<ProductSize> findProductPriceByProductSizeId(List<Long> productSizeId) {
        List<ProductSize> findProductSize = queryFactory.selectFrom(productSize1)
                .join(productSize1.product, product).fetchJoin()
                .where(productSize1.id.in(productSizeId))
                .fetch();

        return findProductSize;


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

    @Override
    public ProductSize findProductSizeByProductSizeId(Long productSizeId) {
        return queryFactory.selectFrom(productSize1)
                .where(productSize1.id.eq(productSizeId))
                .fetchOne();

    }

}
