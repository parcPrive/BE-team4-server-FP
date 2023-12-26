package com.kj.productCategory.entity;

import com.kj.product.entity.Product;
import com.kj.productCategory.dto.ProductCategoryInputDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "mainCategoryId", "subCategoryId", "categoryName"})
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_CATEGORY_ID")
    private Long id;

    @Column(name = "MAINCATEGORY_ID")
    private int mainCategoryId;
    @Column(name = "SUBCATEGORY_ID")
    private int subCategoryId;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "productCategory",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Product> products = new ArrayList<>();

    @Builder
    public ProductCategory(ProductCategoryInputDto productCategoryInputDto){
        this.mainCategoryId = productCategoryInputDto.mainCategoryId();;
        this.subCategoryId = productCategoryInputDto.subCategoryId();
        this.categoryName = productCategoryInputDto.categoryName();
    }
}