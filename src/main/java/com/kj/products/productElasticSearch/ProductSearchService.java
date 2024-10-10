package com.kj.products.productElasticSearch;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.termvectors.Term;
import com.kj.config.ClientConf;
import com.kj.products.product.dto.ProductListDto;
import com.kj.products.product.dto.ProductSearchCondotion;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;
    private final ProductElasticSearchClient productElasticSearchClient;
    private final ClientConf client;
    private final RedisTemplate redisTemplate;


    public PageImpl<ProductListDto> findProductByProductTag(int page, ProductCategorySearchCondition productCategorySearchCondition) {
        Pageable pageable = PageRequest.of(page - 1, 12);
        PageImpl<ProductListDto> findProducts =  productRepository.findProductByProductCategory(pageable,productCategorySearchCondition);
        return findProducts;
    }


    public Map<String, Object> elasticSearchCategoryAll(String searchWord, int page) throws IOException, java.io.IOException {
        SearchResponse<ProductDocument> search = client.client.search(s -> s
                .index("myproduct12")
                        .sort(SortOptions.of(builder -> {
                            FieldSort fieldSort = new FieldSort.Builder().field("product_id").order(SortOrder.Desc).build();
                            return builder.field(fieldSort);
                        }))
                .query(q -> q
                        .term(t -> t
                                .field("product_name")
                                .value(v -> v.stringValue(String.valueOf(searchWord))))
                )
                        .from((page-1)*12)
                        .size(12)
                ,ProductDocument.class);
        List<ESProductReturnDto> productList = new ArrayList<>();
        List<Hit<ProductDocument>> elaResult = search.hits().hits();
        for(Hit<ProductDocument> hit : elaResult){
            productList.add(new ESProductReturnDto(hit.source()));
        }
        int toTalPage = (int) Math.ceil((double)search.hits().total().value() / 12);

        Map<String, Object> result = new HashMap<>();
        result.put("productList", productList);
        result.put("page", toTalPage);

        return result;
    }

    public Map<String, Object> productListMain(int page) throws java.io.IOException {
        ListOperations<String, ESProductReturnDto> listOperations = redisTemplate.opsForList();
        ValueOperations<String, Integer> toTalPage = redisTemplate.opsForValue();
        String key = "productListMain";
        Map<String, Object> resultProductMap = new HashMap<>(); // 결과를 담을 곳
        long size = listOperations.size(key); //레디스에 저장된 메인페이지 리스트가 있는지 확인
        // 레디스에 저장 되어 있다면
        if(size != 0){
            List<ESProductReturnDto> redisProductList = listOperations.range(key, 0, size);
            int redisProductsPage = toTalPage.get("page");
            log.info("레디스에서 가져온 페이지 값 ===>> {}", redisProductsPage);
            resultProductMap.put("page",redisProductsPage);
            resultProductMap.put("productList", redisProductList);
            log.info("레디스!!!!");
            return resultProductMap;
        }
        // 레디스에 없다면 엘라스틱 서치 시작
        SearchResponse<ProductDocument> search = client.client.search(s -> s
                        .index("myproduct12")
                        .sort(SortOptions.of(builder -> {
                            FieldSort fieldSort = new FieldSort.Builder().field("product_id").order(SortOrder.Desc).build();
                            return builder.field(fieldSort);
                        }))
                .query(q -> q
                        .matchAll(new MatchAllQuery.Builder().build())
                )
                        .from((page-1) * 12)
                        .size(12)
        ,ProductDocument.class);
        List<ESProductReturnDto> productList = new ArrayList<>();
        List<Hit<ProductDocument>> elaSearch = search.hits().hits();
        int pages = (int) Math.ceil((double)search.hits().total().value() / 12);
        for(Hit<ProductDocument> hit : elaSearch){
            productList.add(new ESProductReturnDto(hit.source()));
        }

        // redis에 저장
        for(ESProductReturnDto product : productList ){
            listOperations.rightPush(key, product);
        }
        redisTemplate.expire(key,5,TimeUnit.SECONDS);
        toTalPage.set("page", pages,5,TimeUnit.SECONDS);

        // return될 map에 저장
        resultProductMap.put("productList", productList);
        resultProductMap.put("page", pages);
        log.info("엘라스틱!!!!");
        return resultProductMap;

    }

    public Map<String, Object> elasticSearchCategory(int page, ProductSearchCondotion productSearchCondotion) throws java.io.IOException {
        List<Query> querieList = new ArrayList<>();
        Query match = new Query.Builder()
                .match(new MatchQuery.Builder()
                        .field("product_name").query(productSearchCondotion.getSearchWord())
                        .build())
                .build();
        Query search = new Query.Builder()
                .term(t -> t
                        .field("main_product_category_name").value(productSearchCondotion.getCategory())
                ).build();
        querieList.add(match);
        querieList.add(search);
        SearchResponse<ProductDocument> matchSearch = client.client.search(s -> s
                        .index("myproduct12")
                        .sort(SortOptions.of(builder -> {
                            FieldSort fieldSort = new FieldSort.Builder().field("product_id").order(SortOrder.Desc).build();
                            return builder.field(fieldSort);
                        }))
                        .query(p -> p
                                .bool(b -> b
                                        .must(querieList)
                                )
                        )
                        .from((page - 1) * 12)
                        .size(12)
        ,ProductDocument.class);

        List<Hit<ProductDocument>> hit =  matchSearch.hits().hits();
        int toTalPage = (int) Math.ceil((double)matchSearch.hits().total().value() / 12);
        log.info("match ===>>> {}", matchSearch.hits().total().value());
        List<ESProductReturnDto> productList = new ArrayList<>();

        for(Hit<ProductDocument> hit1 : hit){
            log.info("과연 ===>>> {}", hit1.source());
            productList.add(new ESProductReturnDto(hit1.source()));
        }
        log.info("asdasd ====>>> {}", matchSearch.hits().hits());
        Map<String, Object> productMap = new HashMap<>();
        productMap.put("productList", productList);
        productMap.put("page", toTalPage);

        return productMap;
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

