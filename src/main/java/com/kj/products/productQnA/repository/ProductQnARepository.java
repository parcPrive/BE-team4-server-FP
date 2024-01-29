package com.kj.products.productQnA.repository;

import com.kj.products.productQnA.entity.ProductQnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductQnARepository extends JpaRepository<ProductQnA,Long>, ProductQnARepositoryCustom, QuerydslPredicateExecutor<ProductQnA> {
}
