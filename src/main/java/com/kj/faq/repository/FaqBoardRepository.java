package com.kj.faq.repository;

import com.kj.faq.entity.FaqBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FaqBoardRepository extends JpaRepository<FaqBoard,Long> {
    @Query("select b from FaqBoard b")
    Page<FaqBoard> findByAll(Pageable pageable);
}
