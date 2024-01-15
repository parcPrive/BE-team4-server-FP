package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long>, ProductImageRepositoryCustom, QuerydslPredicateExecutor<ProductImage> {

}
