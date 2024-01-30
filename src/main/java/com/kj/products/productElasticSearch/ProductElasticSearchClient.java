//package com.kj.products.productElasticSearch;
//
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import com.kj.config.PaymentFeignConfig;
//import com.kj.products.productElasticSearch.entity.ProductDocument;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@FeignClient(name="elasticsearch", url = "http://localhost:9200",configuration = PaymentFeignConfig.class)
//public interface ProductElasticSearchClient {
//    @GetMapping("/myproduct05/_search")
//    String getSearchData();
//
//    @GetMapping("/myproduct05/_search")
//    String getSearchName(String aaa);
//}
