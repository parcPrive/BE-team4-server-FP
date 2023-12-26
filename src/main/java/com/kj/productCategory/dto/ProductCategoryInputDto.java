package com.kj.productCategory.dto;

public record ProductCategoryInputDto(
        int mainCategoryId,
        int subCategoryId,
        String categoryName
) {
}
