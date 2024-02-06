package com.kj.products.productElasticSearch.dto;

import com.kj.products.productElasticSearch.entity.ProductDocument;
import lombok.Data;

@Data
public class ESProductReturnDto {
    private Long product_id;
    private String product_name;
    private int product_price;
    private String image_name;

    public ESProductReturnDto(ProductDocument productDocument) {
        this.product_id = productDocument.getProduct_id();
        this.product_name = productDocument.getProduct_name();
        this.product_price = productDocument.getProduct_price();
        this.image_name = productDocument.getImage_name();
    }
}
