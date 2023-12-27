package com.kj.product.repository;

import com.querydsl.jpa.impl.JPAQuery;

public interface ProductRepositoryCustom {
    int findByMaxProductId();
}
