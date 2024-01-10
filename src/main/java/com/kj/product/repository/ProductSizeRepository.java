package com.kj.product.repository;

import com.kj.product.entity.ProductSize;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import static com.kj.product.entity.QProductSize.*;

public interface ProductSizeRepository extends JpaRepository<ProductSize,Long>, ProductSizeRepositoryCustom, QuerydslPredicateExecutor<ProductSize> {

}
