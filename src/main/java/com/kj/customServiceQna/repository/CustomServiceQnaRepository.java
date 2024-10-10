package com.kj.customServiceQna.repository;

import com.kj.customServiceQna.entity.CustomServiceQna;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomServiceQnaRepository extends JpaRepository<CustomServiceQna,Long> {
    @Query(value = "with recursive cte AS (" +
            "select q.*, cast(q.id as char(500)) as path from custom_service_qna q " +
            "where q.parent_id IS null " +
            "union all " +
            "select nc2.*,  CONCAT(c.path, '', '') from custom_service_qna nc2 " +
            "inner join cte c on nc2.parent_id = c.id "+
            ")" +
            "select c.* from cte c order by path desc ",
            countQuery = "with recursive cte AS (" +
                    "select q.*, cast(q.id as char(500)) as path from custom_service_qna q " +
                    "where q.parent_id IS null " +
                    "union all " +
                    "select nc2.*,  CONCAT(c.path, '', '') from custom_service_qna nc2 " +
                    "inner join cte c on nc2.parent_id = c.id "+
                    ")" +
                    "select count(*) from cte  order by path desc ",nativeQuery = true)
    Page<CustomServiceQna> findByAll(Pageable pageable);

    @Query("select c from CustomServiceQna c where c.qnaTitle like %:keyword%")
    Page<CustomServiceQna> findAllSearchTitle(@Param("keyword")String keyword, Pageable pageable);
    @Query("select c from CustomServiceQna c where c.qnaCategory like %:keyword%")
    Page<CustomServiceQna> findAllSearchCategory(@Param("keyword")String keyword, Pageable pageable);
    @Query("select c from CustomServiceQna c where c.qnaStatus like %:keyword%")
    Page<CustomServiceQna> findAllSearchStatus(@Param("keyword")String keyword, Pageable pageable);
    @Query(value = "select c from CustomServiceQna c where " +
            "(c.qnaTitle like %:keyword% or " +
            "c.qnaCategory like %:keyword% or " +
            "c.qnaStatus like %:keyword%)")
    Page<CustomServiceQna> findAllSearch(@Param("keyword")String keyword, Pageable pageable);

    @Query("select c from CustomServiceQna c where c.writer.id = :id")
    Page<CustomServiceQna> findByMemberId(@Param("id")Long id,Pageable pageable);

    CustomServiceQna findByParentId(Long id);
}
