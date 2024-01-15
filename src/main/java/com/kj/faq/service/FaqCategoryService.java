package com.kj.faq.service;

import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqCategory;
import com.kj.faq.repository.FaqCategoryRepository;
import com.kj.utils.BigFaqCategory;
import com.kj.utils.SmallFaqCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqCategoryService {
    private  final FaqCategoryRepository faqCategoryRepository;

    public void insertCategory(FaqCategoryDto faqCategoryDto) {
        FaqCategory insertCategory = FaqCategoryDto.toEntity(faqCategoryDto);
        faqCategoryRepository.save(insertCategory);
    }

    public FaqCategory findByFaqCateGoryId(FaqCategoryDto faqCategoryDto) {
        Optional<FaqCategory> faqCategory = faqCategoryRepository.findBySmallFaqCategoryAndBigFaqCategory(SmallFaqCategory.valueOf(faqCategoryDto.getSmallFaqCategory()),
                BigFaqCategory.valueOf(faqCategoryDto.getBigFaqCategory()));
        if (faqCategory.isPresent()){
            return faqCategory.get();
        }
        throw new RuntimeException("카테고리가 존재하지 않습니다.");
    }
}
