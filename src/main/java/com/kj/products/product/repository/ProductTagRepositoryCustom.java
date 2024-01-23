package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductTag;

import java.util.Optional;

public interface ProductTagRepositoryCustom {
    void deleteProductTagbyProductId(Long productId);
    Optional<ProductTag> findProductTagByProductTagName(String productTagName);

    Long productTagIdMaxCount();

}
