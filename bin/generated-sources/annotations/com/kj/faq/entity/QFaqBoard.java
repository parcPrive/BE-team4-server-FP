package com.kj.faq.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFaqBoard is a Querydsl query type for FaqBoard
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFaqBoard extends EntityPathBase<FaqBoard> {

    private static final long serialVersionUID = -798055327L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFaqBoard faqBoard = new QFaqBoard("faqBoard");

    public final QFaqCategory faqCategory;

    public final StringPath faqContent = createString("faqContent");

    public final DateTimePath<java.time.LocalDateTime> faqDate = createDateTime("faqDate", java.time.LocalDateTime.class);

    public final StringPath faqTitle = createString("faqTitle");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.member.entity.QMember writer;

    public QFaqBoard(String variable) {
        this(FaqBoard.class, forVariable(variable), INITS);
    }

    public QFaqBoard(Path<? extends FaqBoard> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFaqBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFaqBoard(PathMetadata metadata, PathInits inits) {
        this(FaqBoard.class, metadata, inits);
    }

    public QFaqBoard(Class<? extends FaqBoard> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.faqCategory = inits.isInitialized("faqCategory") ? new QFaqCategory(forProperty("faqCategory")) : null;
        this.writer = inits.isInitialized("writer") ? new com.kj.member.entity.QMember(forProperty("writer")) : null;
    }

}

