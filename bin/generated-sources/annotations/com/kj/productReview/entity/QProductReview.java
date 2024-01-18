package com.kj.productReview.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductReview is a Querydsl query type for ProductReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductReview extends EntityPathBase<ProductReview> {

    private static final long serialVersionUID = -2107236123L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductReview productReview1 = new QProductReview("productReview1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.products.product.entity.QProduct product;

    public final StringPath productImage = createString("productImage");

    public final StringPath productReview = createString("productReview");

    public final NumberPath<Integer> star = createNumber("star", Integer.class);

    public final StringPath title = createString("title");

    public QProductReview(String variable) {
        this(ProductReview.class, forVariable(variable), INITS);
    }

    public QProductReview(Path<? extends ProductReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductReview(PathMetadata metadata, PathInits inits) {
        this(ProductReview.class, metadata, inits);
    }

    public QProductReview(Class<? extends ProductReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.kj.products.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

