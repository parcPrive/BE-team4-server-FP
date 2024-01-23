package com.kj.noticeComment.repoistory;

import com.kj.noticeComment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select c from Comment c where c.notice.id = :id")
    List<Comment> findByNoticeId(@Param("id") Long id);

}
