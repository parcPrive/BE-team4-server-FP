package com.kj.productCategory.entity;

import com.kj.productCategory.dto.ProductCategoryInputDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUBPRODUCTCATEGORY_ID")
    private Long id;

    @Column(name = "MAINPRODUCTCATEGORY_ID")
    private int mainProductCategoryId;

    @Column
    private String mainProductCategoryName;

    @Column
    private String subProductCategoryName;


    @Builder
    public ProductCategory(ProductCategoryInputDto productCategoryInputDto, String mainProductCategoryName){
        this.mainProductCategoryId = productCategoryInputDto.mainProductCategoryId();
        this.subProductCategoryName = productCategoryInputDto.subProductCategoryName();
        this.mainProductCategoryName = mainProductCategoryName;
    }
}