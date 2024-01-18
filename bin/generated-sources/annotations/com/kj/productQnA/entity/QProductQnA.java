package com.kj.productQnA.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductQnA is a Querydsl query type for ProductQnA
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductQnA extends EntityPathBase<ProductQnA> {

    private static final long serialVersionUID = 1772300233L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductQnA productQnA = new QProductQnA("productQnA");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.products.product.entity.QProduct product;

    public final StringPath productQAContent = createString("productQAContent");

    public final StringPath productQAKind = createString("productQAKind");

    public final StringPath productQATitle = createString("productQATitle");

    public QProductQnA(String variable) {
        this(ProductQnA.class, forVariable(variable), INITS);
    }

    public QProductQnA(Path<? extends ProductQnA> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductQnA(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductQnA(PathMetadata metadata, PathInits inits) {
        this(ProductQnA.class, metadata, inits);
    }

    public QProductQnA(Class<? extends ProductQnA> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new com.kj.products.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

