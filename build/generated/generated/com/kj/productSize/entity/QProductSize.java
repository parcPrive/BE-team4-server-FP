package com.kj.productSize.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.kj.product.entity.ProductSize;
import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductSize is a Querydsl query type for ProductSize
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductSize extends EntityPathBase<ProductSize> {

    private static final long serialVersionUID = -1993265595L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductSize productSize1 = new QProductSize("productSize1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.product.entity.QProduct product;

    public final NumberPath<Integer> productCount = createNumber("productCount", Integer.class);

    public final StringPath productSize = createString("productSize");

    public QProductSize(String variable) {
        this(ProductSize.class, forVariable(variable), INITS);
    }

    public QProductSize(Path<? extends ProductSize> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductSize(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductSize(PathMetadata metadata, PathInits inits) {
        this(ProductSize.class, metadata, inits);
    }

    public QProductSize(Class<? extends ProductSize> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.kj.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

