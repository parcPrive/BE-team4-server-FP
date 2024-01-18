package com.kj.products.productPayment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPayment is a Querydsl query type for ProductPayment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPayment extends EntityPathBase<ProductPayment> {

    private static final long serialVersionUID = 1648549843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPayment productPayment = new QProductPayment("productPayment");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath impUid = createString("impUid");

    public final com.kj.member.entity.QMember member;

    public final NumberPath<Integer> productTotalPrice = createNumber("productTotalPrice", Integer.class);

    public final StringPath status = createString("status");

    public QProductPayment(String variable) {
        this(ProductPayment.class, forVariable(variable), INITS);
    }

    public QProductPayment(Path<? extends ProductPayment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPayment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPayment(PathMetadata metadata, PathInits inits) {
        this(ProductPayment.class, metadata, inits);
    }

    public QProductPayment(Class<? extends ProductPayment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kj.member.entity.QMember(forProperty("member")) : null;
    }

}

