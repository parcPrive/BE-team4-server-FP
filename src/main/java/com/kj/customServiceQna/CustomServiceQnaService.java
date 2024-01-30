package com.kj.customServiceQna;

import com.kj.customServiceQna.dto.CustomServiceQnaDto;
import com.kj.customServiceQna.entity.CustomServiceQna;
import com.kj.customServiceQna.repository.CustomServiceQnaRepository;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomServiceQnaService {
    private final CustomServiceQnaRepository customServiceQnaRepository;
    public Page<CustomServiceQna> qnaList(int page) {
        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC,"qna_Date"));
        Page<CustomServiceQna> qnaList = customServiceQnaRepository.findByAll(pageable);
        return qnaList;
    }

    public void insertQna(CustomServiceQnaDto customServiceQnaDto, CustomUserDetails customUserDetails) {
        if(customServiceQnaDto.getQnaPassword() ==null){
            customServiceQnaDto.setQnaPassword("");
        }
        CustomServiceQna customServiceQna = CustomServiceQnaDto.toEntity(customServiceQnaDto,customUserDetails);
        customServiceQnaRepository.save(customServiceQna);
    }
    @Transactional
    public void answerQna(Long qnaId, CustomServiceQnaDto customServiceQnaDto, CustomUserDetails customUserDetails) {
        if(customServiceQnaDto.getQnaPassword() ==null){
            customServiceQnaDto.setQnaPassword("");
        }
        CustomServiceQna qna = customServiceQnaRepository.findById(qnaId)
                .orElseThrow(()->new RuntimeException("존재 x"));
        qna.update();
        CustomServiceQna customServiceQna = CustomServiceQnaDto.answerToEntity(customServiceQnaDto,customUserDetails,qna);
        customServiceQnaRepository.save(customServiceQna);
    }

    public CustomServiceQna findById(Long id) {
        CustomServiceQna qna = customServiceQnaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재x"));
        return qna;
    }


}
