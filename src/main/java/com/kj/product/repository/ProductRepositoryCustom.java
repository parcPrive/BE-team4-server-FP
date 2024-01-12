package com.kj.product.repository;


import com.kj.product.dto.ProductFindOneDto;
import com.kj.product.dto.ProductListDto;
import com.kj.product.dto.ProductSearchCondotion;
import com.kj.product.entity.Product;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
    int findByMaxProductId();

    Optional<Product> findByProductId(int no);

    PageImpl<ProductListDto> findListProducPage(Pageable pageable, ProductSearchCondotion productSearchCondotion);

    ProductFindOneDto findByProductId1(int productId);

}
