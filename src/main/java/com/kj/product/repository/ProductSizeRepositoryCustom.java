package com.kj.product.repository;

import static com.kj.product.entity.QProductSize.productSize1;

public interface ProductSizeRepositoryCustom {
    void deleteProductSizeByProductId(Long productId);
}
