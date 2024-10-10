package com.kj.codyBoards.codyBoardComments.repository;

import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CodyBoardCommentsRepository extends JpaRepository<CodyBoardComment,Long>, CodyBoardCommentsRepositoryCustum, QuerydslPredicateExecutor<CodyBoardComment> {
}
