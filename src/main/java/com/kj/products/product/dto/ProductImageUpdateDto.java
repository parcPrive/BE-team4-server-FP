package com.kj.products.product.dto;

import lombok.*;


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
