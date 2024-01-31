package com.kj.products.productElasticSearch;

import com.kj.products.product.dto.ProductListDto;
import com.kj.products.product.repository.ProductRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductSearchService {
    private final ProductRepository productRepository;



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

    public void elasticSearchSetting(){
        String setting = """
                
                """;
    }
}
