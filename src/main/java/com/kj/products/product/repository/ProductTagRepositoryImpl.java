package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductTag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    @Override
    public List<ProductTag> findProductTagByProductTagName(String productTagName) {
        List<ProductTag> findproductTag = queryFactory.selectFrom(productTag1)
                .where(productTag1.productTag.eq(productTagName))
                .fetch();
        return findproductTag;
    }

    @Override
    public Long productTagIdMaxCount() {
        Long productTagIdcount = queryFactory.select(productTag1.id.max())
                .from(productTag1)
                .fetchOne();
        if(productTagIdcount == null) return (long)1;
        else return productTagIdcount + 1;
    }

    @Override
    public Long productTagIdminCount(String productTagname) {
        Long ProductTagIdmin = queryFactory.select(productTag1.id.min())
                .from(productTag1)
                .where(productTag1.productTag.eq(productTagname))
                .fetchOne();
        return ProductTagIdmin;
    }

}
