package com.kj.faq.repository;

import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqCategory;
import com.kj.utils.BigFaqCategory;
import com.kj.utils.SmallFaqCategory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FaqCategoryRepository extends JpaRepository<FaqCategory,Long> {

    Optional<FaqCategory> findByBigFaqCategory(BigFaqCategory bigFaqCategory);

    List<FaqCategory> findAllByOrderByBigFaqCategoryAsc();

    Optional<FaqCategory> findBySmallFaqCategory(SmallFaqCategory smallFaqCategory);
}
