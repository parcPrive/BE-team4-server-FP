package com.kj.customServiceQna.dto;

import com.kj.customServiceQna.entity.CustomServiceQna;
import com.kj.member.dto.CustomUserDetails;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomServiceQnaDto {
    private Long id;
    private String qnaCategory;
    private String qnaTitle;
    private String qnaContent;
    private LocalDateTime qnaDate;
    private String qnaStatus;
    private int qnaSecret;
    private String qnaPassword;

    public static CustomServiceQna toEntity(CustomServiceQnaDto customServiceQnaDto, CustomUserDetails customUserDetails) {
        return CustomServiceQna.builder()
                .id(customServiceQnaDto.getId())
                .qnaCategory(customServiceQnaDto.getQnaCategory())
                .qnaContent(customServiceQnaDto.getQnaContent())
                .qnaTitle(customServiceQnaDto.getQnaTitle())
                .qnaDate(LocalDateTime.now())
                .qnaStatus("답변대기")
                .qnaSecret(customServiceQnaDto.getQnaSecret())
                .qnaPassword(new BCryptPasswordEncoder().encode(customServiceQnaDto.getQnaPassword()))
                .writer(customUserDetails.getLoggedMember())
                .build();

    }
    public static CustomServiceQna answerToEntity(CustomServiceQnaDto customServiceQnaDto, CustomUserDetails customUserDetails,CustomServiceQna qna) {
        return CustomServiceQna.builder()
                .id(customServiceQnaDto.getId())
                .qnaCategory("ㄴ")
                .qnaContent(customServiceQnaDto.getQnaContent())
                .qnaTitle(customServiceQnaDto.getQnaTitle())
                .qnaDate(LocalDateTime.now())
                .qnaStatus("답변완료")
                .qnaSecret(customServiceQnaDto.getQnaSecret())
                .qnaPassword(customServiceQnaDto.getQnaPassword())
                .writer(customUserDetails.getLoggedMember())
                .parent(qna)
                .build();

    }
}
