package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductTag;

import java.util.List;
import java.util.Optional;

public interface ProductTagRepositoryCustom {
    void deleteProductTagbyProductId(Long productId);
    List<ProductTag> findProductTagByProductTagName(String productTagName);

    Long productTagIdMaxCount();

    Long productTagIdminCount(String productTagname);

}
