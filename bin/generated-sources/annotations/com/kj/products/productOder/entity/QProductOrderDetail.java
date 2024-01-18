package com.kj.products.productOder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOrderDetail is a Querydsl query type for ProductOrderDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOrderDetail extends EntityPathBase<ProductOrderDetail> {

    private static final long serialVersionUID = -1881069514L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOrderDetail productOrderDetail = new QProductOrderDetail("productOrderDetail");

    public final StringPath deliveryNumber = createString("deliveryNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.member.entity.QMember member;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> productCount = createNumber("productCount", Integer.class);

    public final QProductOrder productOrder;

    public final com.kj.products.productPayment.entity.QProductPayment productPayment;

    public final com.kj.products.product.entity.QProductSize productSize;

    public QProductOrderDetail(String variable) {
        this(ProductOrderDetail.class, forVariable(variable), INITS);
    }

    public QProductOrderDetail(Path<? extends ProductOrderDetail> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOrderDetail(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOrderDetail(PathMetadata metadata, PathInits inits) {
        this(ProductOrderDetail.class, metadata, inits);
    }

    public QProductOrderDetail(Class<? extends ProductOrderDetail> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kj.member.entity.QMember(forProperty("member")) : null;
        this.productOrder = inits.isInitialized("productOrder") ? new QProductOrder(forProperty("productOrder")) : null;
        this.productPayment = inits.isInitialized("productPayment") ? new com.kj.products.productPayment.entity.QProductPayment(forProperty("productPayment"), inits.get("productPayment")) : null;
        this.productSize = inits.isInitialized("productSize") ? new com.kj.products.product.entity.QProductSize(forProperty("productSize"), inits.get("productSize")) : null;
    }

}

