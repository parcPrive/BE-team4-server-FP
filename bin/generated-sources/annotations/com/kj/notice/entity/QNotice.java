package com.kj.notice.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNotice is a Querydsl query type for Notice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNotice extends EntityPathBase<Notice> {

    private static final long serialVersionUID = 168497807L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNotice notice = new QNotice("notice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath noticeCategory = createString("noticeCategory");

    public final StringPath noticeContent = createString("noticeContent");

    public final DateTimePath<java.time.LocalDateTime> noticeDate = createDateTime("noticeDate", java.time.LocalDateTime.class);

    public final StringPath noticeTitle = createString("noticeTitle");

    public final NumberPath<Integer> noticeView = createNumber("noticeView", Integer.class);

    public final com.kj.member.entity.QMember writer;

    public QNotice(String variable) {
        this(Notice.class, forVariable(variable), INITS);
    }

    public QNotice(Path<? extends Notice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNotice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNotice(PathMetadata metadata, PathInits inits) {
        this(Notice.class, metadata, inits);
    }

    public QNotice(Class<? extends Notice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new com.kj.member.entity.QMember(forProperty("writer")) : null;
    }

}

