package com.kj.products.productQnA.repository;

import com.kj.products.productQnA.entity.ProductQnA;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductQnARepositoryCustom {
    long findProductQnAMaxIdByProductQnAId(long ProductQnAId);
    PageImpl<ProductQnA> pageNationProductQnA(Pageable pageable, int productId);
}
