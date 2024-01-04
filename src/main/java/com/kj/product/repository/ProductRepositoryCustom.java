package com.kj.product.repository;


import com.kj.product.dto.ProductUpdateDto;
import com.kj.product.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
    int findByMaxProductId();

    Optional<Product> findByProductId(int no);

}
