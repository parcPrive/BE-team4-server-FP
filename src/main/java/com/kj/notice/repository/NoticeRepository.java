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
}
