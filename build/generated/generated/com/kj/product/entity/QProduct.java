package com.kj.product.entity;

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

    private static final long serialVersionUID = -1761003547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> clickCount = createNumber("clickCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<ProductCart, com.kj.productCart.entity.QProductCart> productCart = this.<ProductCart, com.kj.productCart.entity.QProductCart>createList("productCart", ProductCart.class, com.kj.productCart.entity.QProductCart.class, PathInits.DIRECT2);

    public final com.kj.productCategory.entity.QProductCategory productCategory;

    public final com.kj.productDetailImage.entity.QProductDetailImage productDetailImage;

    public final com.kj.productImage.entity.QProductImage productImages;

    public final ListPath<ProductLike, com.kj.productLike.entity.QProductLike> productLike = this.<ProductLike, com.kj.productLike.entity.QProductLike>createList("productLike", ProductLike.class, com.kj.productLike.entity.QProductLike.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final NumberPath<Integer> productNumber = createNumber("productNumber", Integer.class);

    public final ListPath<com.kj.productQnA.entity.ProductQnA, com.kj.productQnA.entity.QProductQnA> productQnA = this.<com.kj.productQnA.entity.ProductQnA, com.kj.productQnA.entity.QProductQnA>createList("productQnA", com.kj.productQnA.entity.ProductQnA.class, com.kj.productQnA.entity.QProductQnA.class, PathInits.DIRECT2);

    public final ListPath<com.kj.productReview.entity.ProductReview, com.kj.productReview.entity.QProductReview> productReview = this.<com.kj.productReview.entity.ProductReview, com.kj.productReview.entity.QProductReview>createList("productReview", com.kj.productReview.entity.ProductReview.class, com.kj.productReview.entity.QProductReview.class, PathInits.DIRECT2);

    public final ListPath<ProductSize, com.kj.productSize.entity.QProductSize> productSize = this.<ProductSize, com.kj.productSize.entity.QProductSize>createList("productSize", ProductSize.class, com.kj.productSize.entity.QProductSize.class, PathInits.DIRECT2);

    public final StringPath seanson = createString("seanson");

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
        this.productDetailImage = inits.isInitialized("productDetailImage") ? new com.kj.productDetailImage.entity.QProductDetailImage(forProperty("productDetailImage")) : null;
        this.productImages = inits.isInitialized("productImages") ? new com.kj.productImage.entity.QProductImage(forProperty("productImages")) : null;
    }

}

