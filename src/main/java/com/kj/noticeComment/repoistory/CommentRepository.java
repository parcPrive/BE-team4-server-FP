package com.kj.noticeComment.repoistory;

import com.kj.noticeComment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "SELECT *" +
            "FROM notice_comment" +
            " WHERE NOTICE_ID = :idx START WITH parent_id IS NULL" +
            " CONNECT BY PRIOR id = parent_id",nativeQuery = true)
    List<Comment> findByNoticeIds(@Param("idx") Long id);

    @Query(value = "select c from Comment c where c.parent.id = :id")
    List<Comment> findByParentIds(@Param("id") Long id);

}
