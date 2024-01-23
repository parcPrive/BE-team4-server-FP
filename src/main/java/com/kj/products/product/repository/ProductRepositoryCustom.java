package com.kj.products.product.repository;


import com.kj.products.product.dto.ProductFindOneDto;
import com.kj.products.product.dto.ProductListDto;
import com.kj.products.product.dto.ProductSearchCondotion;
import com.kj.products.product.entity.Product;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepositoryCustom {
    int findByMaxProductId();

    Optional<Product> findByProductId(long no);

    PageImpl<ProductListDto> findListProducPage(Pageable pageable, ProductSearchCondotion productSearchCondotion);

    ProductFindOneDto findByProductId1(int productId);

}
