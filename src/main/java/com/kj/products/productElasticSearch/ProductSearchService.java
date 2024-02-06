package com.kj.products.productElasticSearch;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import com.kj.config.ClientConf;
import com.kj.products.product.dto.ProductListDto;
import com.kj.products.product.entity.Product;
import com.kj.products.product.repository.ProductRepository;


import com.kj.products.productElasticSearch.dto.ESProductReturnDto;
import com.kj.products.productElasticSearch.entity.ProductDocument;
import feign.Client;
import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;
    private final ProductElasticSearchClient productElasticSearchClient;
    private final ClientConf client;



    public PageImpl<ProductListDto> findProductByProductTag(int page, ProductCategorySearchCondition productCategorySearchCondition) {
        Pageable pageable = PageRequest.of(page - 1, 12);
//        PageImpl<ProductListDto>
        PageImpl<ProductListDto> findProducts =  productRepository.findProductByProductCategory(pageable,productCategorySearchCondition);
        return findProducts;
    }
//    public Map<String,Object> searchProdudct(String search){
//        SearchHits<ProductDocument> hits = productElasticsearchRepository.findByProduct_name(search);
//        Map<String, Object> result = new HashMap<>();
//        result.put("count", hits.getTotalHits());
//
//        for(SearchHit<ProductDocument> hit : hits){
//            log.info("서치결과 ===> {}", hit.getContent());
//        }
//        return  result;
//
//    }

    public List<ESProductReturnDto> elasticTest(String searchWord) throws IOException, java.io.IOException {
        SearchResponse<ProductDocument> search = client.client.search(s -> s
                .index("myproduct09")
                .query(q -> q
                        .term(t -> t
                                .field("product_name")
                                .value(v -> v.stringValue(String.valueOf(searchWord))))
                ),ProductDocument.class);
        List<ESProductReturnDto> productList = new ArrayList<>();
        List<Hit<ProductDocument>> elaResult = search.hits().hits();
        for(Hit<ProductDocument> hit : elaResult){
            productList.add(new ESProductReturnDto(hit.source()));
        }
//        long l = Mat   search.hits().total().value() / 12;
        Map<String, Object> result = new HashMap<>();
        result.put("productList", productList);
//        result.put()

        return productList;
    }








    public void elasticSearchSetting(){
        String setting = """
                {
                    "settings": {
                        "analysis": {
                                "analyzer": {
                                "my_product_analyzer": {
                                "tokenizer": "my_product_tokenizer"
                            }
                        },
                    "tokenizer": {
                            "my_product_tokenizer": {
                            "type": "nGram",
                            "min_gram": "1",
                            "max_gram": "10"
                            }
                        }
                    },
                    "max_ngram_diff" : "20"
                    }
                }
                """;

        String aaa = productElasticSearchClient.myelasticSearchSettings(setting);
        log.info("엘라스틱 세팅 결과값 ===>> {}", aaa);
    }

    public void elasticSearchMepping(){
        String mapping = """
                mappings:{
                
                {
                "properties": {
                    "product_id": {
                        "type": "long"
                    },
                    "product_name": {
                        "type": "text",
                        "analyzer": "my_product_analyzer"
                    },
                    "product_price": {
                        "type": "long"
                    },
                    "image_name": {
                        "type": "text"
                 }
                }
                }
                """;
        String aaa  = productElasticSearchClient.myelasticSearchMapping(mapping);
        log.info("맵핑값 ===>>>> {}",aaa);
    }
}

