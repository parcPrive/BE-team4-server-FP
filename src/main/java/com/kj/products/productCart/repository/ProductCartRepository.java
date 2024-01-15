package com.kj.products.productCart.repository;

import com.kj.products.productCart.entity.ProductCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductCartRepository extends JpaRepository<ProductCart,Long>,ProductCartRepositoryCustom, QuerydslPredicateExecutor<ProductCart> {
}
