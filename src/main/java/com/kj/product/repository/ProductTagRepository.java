package com.kj.product.repository;

import com.kj.product.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductTagRepository extends JpaRepository<ProductTag, Long>, ProductTagRepositoryCustom, QuerydslPredicateExecutor<ProductTag> {
}
