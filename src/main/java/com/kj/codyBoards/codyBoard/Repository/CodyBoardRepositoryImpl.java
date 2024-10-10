package com.kj.codyBoards.codyBoard.Repository;

import com.kj.codyBoards.codyBoard.dto.CodyBoardListReturnDto;
import com.kj.codyBoards.codyBoard.dto.CodyBoardSearchCondition;
import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.codyBoards.codyBoard.entiry.QCodyBoard;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardCommentsViewDto;
import com.kj.codyBoards.codyBoardComments.entity.QCodyBoardComment;
import com.kj.member.entity.QMember;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.kj.codyBoards.codyBoard.entiry.QCodyBoard.codyBoard;
import static com.kj.codyBoards.codyBoardComments.entity.QCodyBoardComment.codyBoardComment;
import static com.kj.member.entity.QMember.member;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
public class CodyBoardRepositoryImpl implements CodyBoardRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public CodyBoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public PageImpl<CodyBoardListReturnDto> findListCodyBoard(Pageable pageable, CodyBoardSearchCondition codyBoardSearchCondition) {
        QueryResults<Long> codyBoardIds = queryFactory.select(codyBoard.id)
                .from(codyBoard)
                .where(
                        codyBoardSearchCategory(codyBoardSearchCondition)
                )
                .orderBy(codyBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        for(Long aaa : codyBoardIds.getResults()){
            log.info("numbers ===>>{} ", aaa);
        }
        List<CodyBoard> codyBoardList = queryFactory.selectFrom(codyBoard)
                .where(codyBoard.id.in(codyBoardIds.getResults()))
                .join(codyBoard.member, member).fetchJoin()
                .fetch();
        List<CodyBoardListReturnDto> codyBoardListReturnDtoList = new ArrayList<>();
        for(CodyBoard codyBoard1 : codyBoardList){
            codyBoardListReturnDtoList.add(new CodyBoardListReturnDto(codyBoard1));
        }
        PageImpl<CodyBoardListReturnDto> codyBaordList = new PageImpl<>(codyBoardListReturnDtoList,pageable,codyBoardIds.getTotal());
        return codyBaordList;
    }

    @Override
    public CodyBoard findByCodyBoard(Long codyBoardId) {
        CodyBoard codyBoard1 = queryFactory.selectFrom(codyBoard)
                .join(codyBoard.member, member).fetchJoin()
                .leftJoin(codyBoard.codyBoardComments,codyBoardComment).fetchJoin()
                .leftJoin(codyBoardComment.parent).fetchJoin()
//                .leftJoin(codyBoardComment.member, member).fetchJoin()
                .where(codyBoard.id.eq(codyBoardId)
//                        codyBoardComment.codyBoard.id.eq(codyBoardId)
                        )
                .orderBy(
                        codyBoardComment.sortNum.desc()
                        )
                .fetchOne();
//        CodyBoardCommentsViewDto aaa = new CodyBoardCommentsViewDto(codyBoard1);
//        log.info("여기 한번볼까요?? ==>>> {}", aaa.getCodyBoardComment());
        log.info("?? ===>> {}", codyBoard1.getMember().getUserName());
        return codyBoard1;
    }


    private BooleanExpression codyBoardSearchCategory(CodyBoardSearchCondition codyBoardSearchCondition) {
        if (!hasText(codyBoardSearchCondition.getSearchCategory())) return null;
        else if (codyBoardSearchCondition.getSearchCategory().equals("all")) {
            return codyBoard.codyBoardTitle.like("%" + codyBoardSearchCondition.getSearchWord() + "%")
                    .or(codyBoard.content.like("%" + codyBoardSearchCondition.getSearchWord() + "%"))
                    .or(codyBoard.member.nickName.like("%" + codyBoardSearchCondition.getSearchWord() + "%"));
        } else if (codyBoardSearchCondition.getSearchCategory().equals("codyBoardTitle")) {
            return codyBoard.codyBoardTitle.like("%" + codyBoardSearchCondition.getSearchWord() + "%");
        } else if (codyBoardSearchCondition.getSearchCategory().equals("codyBoardcContent")) {
            return codyBoard.content.like("%" + codyBoardSearchCondition.getSearchWord() + "%");
        } else if (codyBoardSearchCondition.getSearchCategory().equals("codyBoardUserName")) {
            return codyBoard.member.nickName.like("%" + codyBoardSearchCondition.getSearchWord() + "%");
        } else {
            return null;
        }

    }
}
