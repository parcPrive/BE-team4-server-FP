package com.kj.productCategory.Repository;

import com.kj.productCategory.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>,ProductCategoryRepositoryCustom, QuerydslPredicateExecutor<ProductCategory> {
}
