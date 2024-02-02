package com.kj.codyBoards.codyBoardComments.repository;

import com.kj.codyBoards.codyBoardComments.entity.QCodyBoardComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.kj.codyBoards.codyBoardComments.entity.QCodyBoardComment.codyBoardComment;

public class CodyBoardCommentsRepositoryImpl implements CodyBoardCommentsRepositoryCustum{
    private final JPAQueryFactory queryFactory;

    public CodyBoardCommentsRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long commentMax() {
        Long maxNum = queryFactory.select(codyBoardComment.id.max())
                .from(codyBoardComment)
                .fetchOne();
        if(maxNum == null || maxNum ==0){
            return (long)1;
        }
        return maxNum;

    }
}
