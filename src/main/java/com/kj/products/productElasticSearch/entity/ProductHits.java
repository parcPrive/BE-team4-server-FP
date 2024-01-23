package com.kj.products.productElasticSearch.entity;

import lombok.Data;

import java.util.List;
@Data
public class ProductHits {
    private List<Object> hits;
}
