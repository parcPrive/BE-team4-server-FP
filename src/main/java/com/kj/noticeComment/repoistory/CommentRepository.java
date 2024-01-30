package com.kj.noticeComment.repoistory;

import com.kj.noticeComment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "with recursive CTE(id,comment_content, notice_id, parent_id ,writer_id,comment_date, depth,path) as " +
            "(" +
            " select id, comment_content, notice_id, parent_id,writer_id,comment_date, 0, cast(id as char(200)) from notice_comment where parent_id IS null and notice_id =:idx  union all " +
            " select nc2.id, nc2.comment_content, nc2.notice_id, nc2.parent_id,nc2.writer_id,nc2.comment_date, c.depth +1,  CONCAT(c.path, '-', nc2.id) from notice_comment nc2 " +
            " inner join CTE c on nc2.parent_id = c.id" +
            ")" +
            "select * from cte order by path",nativeQuery = true)
    List<Comment> findByNoticeIds(@Param("idx") Long id);

    @Query(value = "select c from Comment c where c.parent.id = :id")
    List<Comment> findByParentIds(@Param("id") Long id);

}
