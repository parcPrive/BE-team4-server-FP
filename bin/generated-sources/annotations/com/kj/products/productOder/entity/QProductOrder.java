package com.kj.products.productOder.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductOrder is a Querydsl query type for ProductOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOrder extends EntityPathBase<ProductOrder> {

    private static final long serialVersionUID = -2007655547L;

    public static final QProductOrder productOrder = new QProductOrder("productOrder");

    public final StringPath deliveryAddress = createString("deliveryAddress");

    public final StringPath deliveryDetailAddress = createString("deliveryDetailAddress");

    public final StringPath deliveryRequest = createString("deliveryRequest");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath userName = createString("userName");

    public QProductOrder(String variable) {
        super(ProductOrder.class, forVariable(variable));
    }

    public QProductOrder(Path<? extends ProductOrder> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductOrder(PathMetadata metadata) {
        super(ProductOrder.class, metadata);
    }

}

