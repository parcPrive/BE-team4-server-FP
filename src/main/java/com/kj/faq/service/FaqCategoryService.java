package com.kj.faq.service;

import com.kj.customServiceQna.entity.CustomServiceQna;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqCategory;
import com.kj.faq.repository.FaqCategoryRepository;
import com.kj.utils.BigFaqCategory;
import com.kj.utils.SmallFaqCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqCategoryService {
    private  final FaqCategoryRepository faqCategoryRepository;

    public void insertCategory(FaqCategoryDto faqCategoryDto) {
        Optional<FaqCategory> checkFaqCategory = faqCategoryRepository.findBySmallFaqCategory(SmallFaqCategory.valueOf(faqCategoryDto.getSmallFaqCategory()));
        if(!checkFaqCategory.isPresent()){
        FaqCategory insertCategory = FaqCategoryDto.toEntity(faqCategoryDto);
        faqCategoryRepository.save(insertCategory);
        }
        throw new RuntimeException("이미 존재합니다.");
    }


    public FaqCategory findByCategoryId(Long id) {
        Optional<FaqCategory> faqCategory = faqCategoryRepository.findById(id);
        if (faqCategory.isPresent()){
            return faqCategory.get();
        }
        throw new RuntimeException("카테고리가 존재하지 않습니다.");
    }
    public List<FaqCategory> findByAllCategory() {
        List<FaqCategory> faqCategoryList = faqCategoryRepository.findAllByOrderByBigFaqCategoryAsc();
        return faqCategoryList;
    }

    public boolean deleteCategory(Long id) {
        Optional<FaqCategory> category = faqCategoryRepository.findById(id);
        if (category.isPresent()){
            faqCategoryRepository.delete(category.get());
            return true;
        }else {
            return false;
        }
    }
}
