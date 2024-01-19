package com.kj.products.product.repository;

import com.kj.products.product.entity.ProductSize;

import java.util.List;

public interface ProductSizeRepositoryCustom {
    List<ProductSize> findProductPriceByProductSizeId(List<Long> productSizeId);
    void deleteProductSizeByProductId(Long productId);

    ProductSize findProductSizeByProductSizeId(Long productSizeId);
}
