package com.kj.products.productOder.repository;

import com.kj.products.productOder.entity.ProductOrderProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductOrderProductDetailRepository extends JpaRepository<ProductOrderProductDetail, Long>, ProductOrderProductDetailRepositoryCustom, QuerydslPredicateExecutor<ProductOrderProductDetail> {
}
