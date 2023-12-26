package com.kj.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductDetailImage is a Querydsl query type for ProductDetailImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetailImage extends EntityPathBase<ProductDetailImage> {

    private static final long serialVersionUID = -842651035L;

    public static final QProductDetailImage productDetailImage = new QProductDetailImage("productDetailImage");

    public final StringPath id = createString("id");

    public final StringPath productDatailImage = createString("productDatailImage");

    public QProductDetailImage(String variable) {
        super(ProductDetailImage.class, forVariable(variable));
    }

    public QProductDetailImage(Path<? extends ProductDetailImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductDetailImage(PathMetadata metadata) {
        super(ProductDetailImage.class, metadata);
    }

}

