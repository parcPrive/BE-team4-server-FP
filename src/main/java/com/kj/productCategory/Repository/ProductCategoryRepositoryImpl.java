package com.kj.productCategory.Repository;

import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.productCategory.dto.QProductCategoryfindDto;
import com.kj.productCategory.entity.ProductCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.kj.productCategory.entity.QProductCategory.productCategory;

@Slf4j
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

    @Override
    public List<ProductCategory> findProductSubCategoryByProductMainCategoryId(int productMainCategoryId) {
        List<ProductCategory> findProductSubCategoryList = queryFactory.selectFrom(productCategory)
                .where(productCategory.mainProductCategoryId.eq(productMainCategoryId))
                .fetch();
        for(ProductCategory result : findProductSubCategoryList){
            log.info("결과값 ===>> {}", result.getSubProductCategoryName());
        }
        return findProductSubCategoryList;
    }
}
