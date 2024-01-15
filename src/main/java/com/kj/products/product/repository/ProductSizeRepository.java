package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductSizeRepository extends JpaRepository<ProductSize,Long>, ProductSizeRepositoryCustom, QuerydslPredicateExecutor<ProductSize> {

}
