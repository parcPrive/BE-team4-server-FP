package com.kj.product.dto;

import com.kj.product.entity.Product;
import com.kj.product.entity.ProductImage;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@ToString
public class ProductImageUpdateDto {
    private Long id;
    private String imageName;
    private int thubmnail;
    private String bucketName;

    //    @QueryProjection
//    public ProductImageUpdateDto(Long id, String imageName, int thubmnail, String bucketName) {
//        this.id = id;
//        this.imageName = imageName;
//        this.thubmnail = thubmnail;
//        this.bucketName = bucketName;
//    }
}
