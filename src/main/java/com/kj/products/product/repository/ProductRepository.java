package com.kj.products.product.repository;

import com.kj.products.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product,Long>, ProductRepositoryCustom, QuerydslPredicateExecutor<Product> {
}
