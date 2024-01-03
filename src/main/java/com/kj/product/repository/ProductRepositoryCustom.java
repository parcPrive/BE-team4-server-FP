package com.kj.product.repository;


import com.kj.product.dto.ProductUpdateDto;
import com.kj.product.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    int findByMaxProductId();

    Product findByProductId(int no);

}
