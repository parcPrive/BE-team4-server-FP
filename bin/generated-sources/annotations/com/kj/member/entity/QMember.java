package com.kj.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -702398829L;

    public static final QMember member = new QMember("member1");

    public final StringPath address = createString("address");

    public final StringPath addressDetail = createString("addressDetail");

    public final ListPath<com.kj.deleteMember.entity.DeleteMember, com.kj.deleteMember.entity.QDeleteMember> deleteMemberList = this.<com.kj.deleteMember.entity.DeleteMember, com.kj.deleteMember.entity.QDeleteMember>createList("deleteMemberList", com.kj.deleteMember.entity.DeleteMember.class, com.kj.deleteMember.entity.QDeleteMember.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final ListPath<com.kj.faq.entity.FaqBoard, com.kj.faq.entity.QFaqBoard> faqBoardList = this.<com.kj.faq.entity.FaqBoard, com.kj.faq.entity.QFaqBoard>createList("faqBoardList", com.kj.faq.entity.FaqBoard.class, com.kj.faq.entity.QFaqBoard.class, PathInits.DIRECT2);

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath levels = createString("levels");

    public final ListPath<com.kj.log.entity.Log, com.kj.log.entity.QLog> logList = this.<com.kj.log.entity.Log, com.kj.log.entity.QLog>createList("logList", com.kj.log.entity.Log.class, com.kj.log.entity.QLog.class, PathInits.DIRECT2);

    public final EnumPath<com.kj.utils.Mobile> mobile = createEnum("mobile", com.kj.utils.Mobile.class);

    public final StringPath nickName = createString("nickName");

    public final ListPath<com.kj.notice.entity.Notice, com.kj.notice.entity.QNotice> noticeList = this.<com.kj.notice.entity.Notice, com.kj.notice.entity.QNotice>createList("noticeList", com.kj.notice.entity.Notice.class, com.kj.notice.entity.QNotice.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath postCode = createString("postCode");

    public final StringPath profileImageUrl = createString("profileImageUrl");

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final EnumPath<com.kj.utils.Role> role = createEnum("role", com.kj.utils.Role.class);

    public final StringPath userId = createString("userId");

    public final StringPath userName = createString("userName");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

