package com.kj.products.productElasticSearch.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
//@Document(indexName = "products01")
//@AllArgsConstructor
@NoArgsConstructor
//@Setting(replicas = 0)
@Data
public class ProductDocument {
    //    @Field(name = "product_id", type = FieldType.Long)
//    @Id
    private Long product_id;

//    @Field(name = "product_name", type = FieldType.Text)
    private String product_name;

//    @Field(name = "product_price", type = FieldType.Integer)
    private Integer product_price;

//    @Field(name = "image_name", type = FieldType.Text)
    private String image_name;

//    @Field(name ="createdat", type = FieldType.Date)
    private String createdat;

    private String main_product_category_name;

    private String sub_product_category_name;


}
