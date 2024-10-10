package com.kj.faq.dto;

import com.kj.faq.entity.FaqCategory;
import com.kj.utils.BigFaqCategory;
import com.kj.utils.SmallFaqCategory;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqCategoryDto {
    private Long id;
    private String bigFaqCategory;
    private String smallFaqCategory;

    public static FaqCategory toEntity(FaqCategoryDto faqCategoryDto){
        return FaqCategory.builder()
                .id(faqCategoryDto.getId())
                .bigFaqCategory(BigFaqCategory.valueOf(faqCategoryDto.getBigFaqCategory()))
                .smallFaqCategory(SmallFaqCategory.valueOf(faqCategoryDto.getSmallFaqCategory()))
                .build();
    }


}
