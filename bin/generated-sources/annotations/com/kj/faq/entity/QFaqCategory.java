package com.kj.faq.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFaqCategory is a Querydsl query type for FaqCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaqCategory extends EntityPathBase<FaqCategory> {

    private static final long serialVersionUID = 512545187L;

    public static final QFaqCategory faqCategory = new QFaqCategory("faqCategory");

    public final EnumPath<com.kj.utils.BigFaqCategory> bigFaqCategory = createEnum("bigFaqCategory", com.kj.utils.BigFaqCategory.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<com.kj.utils.SmallFaqCategory> smallFaqCategory = createEnum("smallFaqCategory", com.kj.utils.SmallFaqCategory.class);

    public QFaqCategory(String variable) {
        super(FaqCategory.class, forVariable(variable));
    }

    public QFaqCategory(Path<? extends FaqCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFaqCategory(PathMetadata metadata) {
        super(FaqCategory.class, metadata);
    }

}

