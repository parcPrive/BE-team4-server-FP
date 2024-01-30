package com.kj.customServiceQna.repository;

import com.kj.customServiceQna.entity.CustomServiceQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomServiceQnaRepository extends JpaRepository<CustomServiceQna,Long> {
    @Query(value = "with recursive CTE as " +
            "(" +
            " select id, qna_content, parent_id,writer_id,qna_date,qna_category,qna_password,qna_secret,qna_status,qna_title, cast(id as char(200)) as path from custom_service_qna " +
            "where parent_id IS null union all " +
            " select nc2.id, nc2.qna_content, nc2.parent_id,nc2.writer_id,nc2.qna_date,nc2.qna_category,nc2.qna_password,nc2.qna_secret,nc2.qna_status,nc2.qna_title,  CONCAT(c.path, '-', nc2.id) from custom_service_qna nc2 " +
            " inner join CTE c on nc2.parent_id = c.id "+
            ")" +
            "select * from cte order by path desc ",nativeQuery = true)
    Page<CustomServiceQna> findByAll(Pageable pageable);
}
