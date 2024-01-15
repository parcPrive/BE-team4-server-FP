package com.kj.faq.dto;

import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.member.dto.CustomUserDetails;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FaqBoardDto {
    private Long id;
    private String faqTitle;
    private String faqContent;
    private LocalDateTime faqDate;
    private List<FaqCategory> faqCategoryList;

    public static FaqBoardDto fromEntity(FaqBoard faqBoard) {
        return FaqBoardDto.builder()
                .id(faqBoard.getId())
                .faqTitle(faqBoard.getFaqTitle())
                .faqContent(faqBoard.getFaqContent())
                .faqDate(LocalDateTime.now())
                .build();
    }

    public static FaqBoard toEntity(FaqBoardDto faqBoardDto, CustomUserDetails customUserDetails, FaqCategory faqCategory) {
        return FaqBoard.builder()
                .id(faqBoardDto.getId())
                .faqTitle(faqBoardDto.getFaqTitle())
                .faqContent(faqBoardDto.getFaqContent())
                .faqDate(LocalDateTime.now())
                .writer(customUserDetails.getLoggedMember())
                .faqCategory(faqCategory)
                .build();
    }

}
