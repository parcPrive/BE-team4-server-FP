package com.kj.productCategory.Repository;

import com.kj.productCategory.dto.ProductCategoryfindDto;
import com.kj.productCategory.dto.QProductCategoryfindDto;
import com.kj.productCategory.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryRepositoryCustom {
    List<ProductCategoryfindDto> findAllProductCategory();
}
