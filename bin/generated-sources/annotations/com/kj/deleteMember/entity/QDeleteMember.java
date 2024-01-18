package com.kj.deleteMember.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeleteMember is a Querydsl query type for DeleteMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeleteMember extends EntityPathBase<DeleteMember> {

    private static final long serialVersionUID = 1714994665L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeleteMember deleteMember = new QDeleteMember("deleteMember");

    public final DateTimePath<java.time.LocalDateTime> deleteDate = createDateTime("deleteDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.kj.member.entity.QMember member;

    public QDeleteMember(String variable) {
        this(DeleteMember.class, forVariable(variable), INITS);
    }

    public QDeleteMember(Path<? extends DeleteMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeleteMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeleteMember(PathMetadata metadata, PathInits inits) {
        this(DeleteMember.class, metadata, inits);
    }

    public QDeleteMember(Class<? extends DeleteMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.kj.member.entity.QMember(forProperty("member")) : null;
    }

}

