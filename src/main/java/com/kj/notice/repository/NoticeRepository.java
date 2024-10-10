package com.kj.notice.repository;

import com.kj.faq.entity.FaqBoard;
import com.kj.notice.entity.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    @Query("select n from Notice n")
    Page<Notice> findByAll(Pageable pageable);

    @Query(value = "SELECT *" +
            "FROM (SELECT n.*, ROWNUM AS num FROM" +
            "(SELECT * FROM NOTICE ORDER BY notice_date asc) n) where num BETWEEN :no-1 AND :no+1",nativeQuery = true)
    List<Notice> findByBetweenNum(@Param("no") int no);

    @Query(value ="SELECT *" +
            "FROM notice WHERE id = " +
            "(SELECT prev_no FROM (SELECT id, LAG(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findPreNotice(@Param("id") Long id);

    @Query(value ="SELECT *" +
            "FROM notice WHERE id = " +
            "(SELECT prev_no FROM (SELECT id, LEAD(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findNextNotice(@Param("id") Long id);
    @Query(value ="SELECT *" +
            "FROM notice WHERE notice_title like %:keyword% and id = " +
            "(SELECT prev_no FROM (SELECT id, LEAD(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findSearchTitleNextNotice(@Param("keyword") String keyword, @Param("id") Long id);
    @Query(value ="SELECT *" +
            "FROM notice WHERE notice_title like %:keyword% and id = " +
            "(SELECT prev_no FROM (SELECT id, LAG(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findSearchTitleprevNotice(@Param("keyword") String keyword, @Param("id") Long id);
    @Query(value ="SELECT *" +
            "FROM notice WHERE notice_category like %:keyword% and id = " +
            "(SELECT prev_no FROM (SELECT id, LEAD(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findSearchCategoryNextNotice(@Param("keyword") String keyword, @Param("id") Long id);
    @Query(value ="SELECT *" +
            "FROM notice WHERE notice_category like %:keyword% and id = " +
            "(SELECT prev_no FROM (SELECT id, LAG(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findSearchCategoryprevNotice(@Param("keyword") String keyword, @Param("id") Long id);
    @Query(value ="SELECT *" +
            "FROM notice WHERE (notice_category like %:keyword% or notice_title like %:keyword%) and id = " +
            "(SELECT prev_no FROM (SELECT id, LEAD(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findSearchAllNextNotice(@Param("keyword") String keyword, @Param("id") Long id);
    @Query(value ="SELECT *" +
            "FROM notice WHERE (notice_category like %:keyword% or notice_title like %:keyword%) and id = " +
            "(SELECT prev_no FROM (SELECT id, LAG(id, 1, -1) OVER(ORDER BY id) AS prev_no FROM NOTICE) B WHERE id = :id)" ,nativeQuery = true)
    Notice findSearchAllprevNotice(@Param("keyword") String keyword, @Param("id") Long id);



    @Query("select n from Notice n where n.noticeTitle like %:keyword%")
    Page<Notice> findAllSearchTitle(@Param("keyword") String keyword, Pageable pageable);
    @Query("select n from Notice n where n.noticeCategory like %:keyword%")
    Page<Notice> findAllSearchCategory(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select n from Notice n where " +
            "(n.noticeTitle like %:keyword% or " +
            "n.noticeCategory like %:keyword% )")
    Page<Notice> findAllSearch(@Param("keyword") String keyword, Pageable pageable);
}
