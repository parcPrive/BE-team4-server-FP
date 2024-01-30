//package com.kj.products.productElasticSearch.repository;
//
//import com.kj.products.productElasticSearch.entity.ProductDocument;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//
//public interface ProductElasticsearchRepository  extends ElasticsearchRepository<ProductDocument, Long> {
//    SearchHits<ProductDocument> findByProduct_name(String search);
//
//}
