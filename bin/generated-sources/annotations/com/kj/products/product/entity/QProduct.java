package com.kj.products.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -1851055253L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> clickCount = createNumber("clickCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.productCategory.entity.QProductCategory productCategory;

    public final StringPath productDatailImage = createString("productDatailImage");

    public final StringPath productDetailImageBucket = createString("productDetailImageBucket");

    public final ListPath<ProductImage, QProductImage> productImages = this.<ProductImage, QProductImage>createList("productImages", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final StringPath productNumber = createString("productNumber");

    public final NumberPath<Integer> productPrice = createNumber("productPrice", Integer.class);

    public final ListPath<com.kj.productQnA.entity.ProductQnA, com.kj.productQnA.entity.QProductQnA> productQnA = this.<com.kj.productQnA.entity.ProductQnA, com.kj.productQnA.entity.QProductQnA>createList("productQnA", com.kj.productQnA.entity.ProductQnA.class, com.kj.productQnA.entity.QProductQnA.class, PathInits.DIRECT2);

    public final ListPath<com.kj.productReview.entity.ProductReview, com.kj.productReview.entity.QProductReview> productReview = this.<com.kj.productReview.entity.ProductReview, com.kj.productReview.entity.QProductReview>createList("productReview", com.kj.productReview.entity.ProductReview.class, com.kj.productReview.entity.QProductReview.class, PathInits.DIRECT2);

    public final StringPath productSeason = createString("productSeason");

    public final ListPath<ProductSize, QProductSize> productSize = this.<ProductSize, QProductSize>createList("productSize", ProductSize.class, QProductSize.class, PathInits.DIRECT2);

    public final ListPath<ProductTag, QProductTag> productTags = this.<ProductTag, QProductTag>createList("productTags", ProductTag.class, QProductTag.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> updateedAt = createDateTime("updateedAt", java.time.LocalDateTime.class);

    public final StringPath writer = createString("writer");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productCategory = inits.isInitialized("productCategory") ? new com.kj.productCategory.entity.QProductCategory(forProperty("productCategory")) : null;
    }

}

