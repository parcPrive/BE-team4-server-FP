package com.kj.product.entity;

import com.kj.product.entity.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MAINCATEGORY_ID")
    private int mainCategoryID;
    @Column(name = "SUBCATEGORY_ID")
    private int subCategoryID;
    private String categoryName;

    @OneToMany(mappedBy = "productCategory",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Product> products = new ArrayList<>();
}
