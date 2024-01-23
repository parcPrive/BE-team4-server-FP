package com.kj.products.productElasticSearch.entity;


import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

@Data
public class ElasticSearchHits {
    private Object hits;
}
