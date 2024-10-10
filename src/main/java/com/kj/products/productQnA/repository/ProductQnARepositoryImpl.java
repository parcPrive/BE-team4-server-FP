package com.kj.products.productQnA.repository;

import com.kj.products.productQnA.entity.ProductQnA;
import com.kj.products.productQnACategory.entity.QProductQnACategory;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.kj.products.productQnA.entity.QProductQnA.productQnA;
import static com.kj.products.productQnACategory.entity.QProductQnACategory.productQnACategory;
@Slf4j
public class ProductQnARepositoryImpl implements ProductQnARepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public ProductQnARepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long findProductQnAMaxIdByProductQnAId(long ProductQnAId) {
        Long productQnAIdMax = queryFactory.select(productQnA.id.max())
                .from(productQnA)
                .fetchOne();
        if(productQnAIdMax == null){
            productQnAIdMax = (long)1;
            return productQnAIdMax;
        }
        return productQnAIdMax + 1;


    }

    @Override
    public PageImpl<ProductQnA> pageNationProductQnA(Pageable pageable, int productId) {
        QueryResults<Long> productQnAIds = queryFactory.select(productQnA.id)
                .from(productQnA)
                .where(productQnA.parent.isNull())
                .orderBy(productQnA.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();



        List<ProductQnA> productQnAList= queryFactory.selectFrom(productQnA)
                .leftJoin(productQnA.children).fetchJoin()
                .join(productQnA.productQnACategory, productQnACategory).fetchJoin()
                .where(
                        productQnA.product.id.eq((long)productId),
                        productQnA.id.in(productQnAIds.getResults())
                )
                .orderBy(
                        productQnA.sortNum.desc()
                )
                .fetch();
        PageImpl<ProductQnA> productQnAPageList = new PageImpl<>(productQnAList, pageable, productQnAIds.getTotal());

        return productQnAPageList;

    }
}
