package com.kj.products.productCart.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductCart is a Querydsl query type for ProductCart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductCart extends EntityPathBase<ProductCart> {

    private static final long serialVersionUID = 384780651L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductCart productCart = new QProductCart("productCart");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.member.entity.QMember member;

    public final NumberPath<Integer> productCount = createNumber("productCount", Integer.class);

    public final com.kj.products.product.entity.QProductSize productSize;

    public QProductCart(String variable) {
        this(ProductCart.class, forVariable(variable), INITS);
    }

    public QProductCart(Path<? extends ProductCart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductCart(PathMetadata metadata, PathInits inits) {
        this(ProductCart.class, metadata, inits);
    }

    public QProductCart(Class<? extends ProductCart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kj.member.entity.QMember(forProperty("member")) : null;
        this.productSize = inits.isInitialized("productSize") ? new com.kj.products.product.entity.QProductSize(forProperty("productSize"), inits.get("productSize")) : null;
    }

}

