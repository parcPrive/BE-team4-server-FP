package com.kj.products.productElasticSearch;

import lombok.Data;

@Data
public class ProductCategorySearchCondition {
    private String mainCategory;
    private String subCategory;
}
