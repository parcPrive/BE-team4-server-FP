package com.kj.productCategory.Repository;

import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.productCategory.dto.QProductCategoryfindDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.kj.productCategory.entity.QProductCategory.productCategory;

public class ProductCategoryRepositoryImpl implements ProductCategoryRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public ProductCategoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<ProductCategoryfindDto> findAllProductCategory() {
        List<ProductCategoryfindDto> result =queryFactory
                .select(new QProductCategoryfindDto(
                        productCategory.id.as("subProductCategoryId"),
                        productCategory.mainProductCategoryId,
                        productCategory.subProductCategoryName
                )).from(productCategory)
                .fetch();
        return result;
    }
}
