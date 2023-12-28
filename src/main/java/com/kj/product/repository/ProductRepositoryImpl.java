package com.kj.product.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.kj.product.entity.QProduct.*;


public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int findByMaxProductId() {
        Long result = queryFactory
                .select(product.id.max())
                .from(product)
                .fetchOne();
        if(result == null){
            return 1;
        }
        return Integer.parseInt(String.valueOf(result)) + 1;


    }
}
