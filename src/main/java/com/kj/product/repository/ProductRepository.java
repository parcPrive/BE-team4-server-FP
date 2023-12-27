package com.kj.product.repository;

import com.kj.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product,Long>, ProductRepositoryCustom, QuerydslPredicateExecutor<Product> {
}
