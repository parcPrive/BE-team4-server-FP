package com.kj.productCategory.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductCategory is a Querydsl query type for ProductCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCategory extends EntityPathBase<ProductCategory> {

    private static final long serialVersionUID = 1789525541L;

    public static final QProductCategory productCategory = new QProductCategory("productCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> mainProductCategoryId = createNumber("mainProductCategoryId", Integer.class);

    public final StringPath mainProductCategoryName = createString("mainProductCategoryName");

    public final StringPath subProductCategoryName = createString("subProductCategoryName");

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

