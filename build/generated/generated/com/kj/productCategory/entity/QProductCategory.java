package com.kj.productCategory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.kj.product.entity.ProductCategory;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCategory is a Querydsl query type for ProductCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCategory extends EntityPathBase<ProductCategory> {

    private static final long serialVersionUID = 1789525541L;

    public static final QProductCategory productCategory = new QProductCategory("productCategory");

    public final StringPath categoryName = createString("categoryName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> mainCategoryID = createNumber("mainCategoryID", Integer.class);

    public final ListPath<com.kj.product.entity.Product, com.kj.product.entity.QProduct> products = this.<com.kj.product.entity.Product, com.kj.product.entity.QProduct>createList("products", com.kj.product.entity.Product.class, com.kj.product.entity.QProduct.class, PathInits.DIRECT2);

    public final NumberPath<Integer> subCategoryID = createNumber("subCategoryID", Integer.class);

    public QProductCategory(String variable) {
        super(ProductCategory.class, forVariable(variable));
    }

    public QProductCategory(Path<? extends ProductCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductCategory(PathMetadata metadata) {
        super(ProductCategory.class, metadata);
    }

}

