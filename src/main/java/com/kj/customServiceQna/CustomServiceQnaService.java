package com.kj.customServiceQna;

import com.kj.customServiceQna.dto.CustomServiceQnaDto;
import com.kj.customServiceQna.entity.CustomServiceQna;
import com.kj.customServiceQna.repository.CustomServiceQnaRepository;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomServiceQnaService {
    private final CustomServiceQnaRepository customServiceQnaRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Page<CustomServiceQna> qnaList(int page) {
        Pageable pageable = PageRequest.of(page,10, Sort.by(Sort.Direction.DESC,"path"));
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


    public boolean qnaLogin(Long id, String qnaPassword) {
        CustomServiceQna qna = customServiceQnaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재x"));
       if(bCryptPasswordEncoder.matches(qnaPassword,qna.getQnaPassword())){

           return true;
       }else {
           return false;
       }
    }

    public Page<CustomServiceQna> searchQnaList(String category, String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "qnaDate"));
        if (category.equals("qnaTitle")) {
            Page<CustomServiceQna> qna = customServiceQnaRepository.findAllSearchTitle(keyword,pageable);
            return qna;
        } else if(category.equals("qnaCategory")) {
            Page<CustomServiceQna> qna = customServiceQnaRepository.findAllSearchCategory(keyword,pageable);
            return qna;
        }else if(category.equals("qnaStatus")) {
            Page<CustomServiceQna> qna = customServiceQnaRepository.findAllSearchStatus(keyword,pageable);
            return qna;
        }else {
            //전체검색
            Page<CustomServiceQna> qna = customServiceQnaRepository.findAllSearch(keyword,pageable);
            return qna;
        }
    }

    public CustomServiceQna findByUpdateId(Long id, CustomServiceQnaDto customServiceQnaDto) {
        CustomServiceQna qna = customServiceQnaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("존재x"));
        if(customServiceQnaDto.getQnaSecret()==1 && customServiceQnaDto.getQnaPassword() ==null){
            qna.updateQna(customServiceQnaDto);
        }else if(customServiceQnaDto.getQnaSecret()==1 && customServiceQnaDto.getQnaPassword() !=null){
            qna.updatePasswordQna(customServiceQnaDto);
        }else if(customServiceQnaDto.getQnaSecret()==0){
            qna.updateNoPasswordQna(customServiceQnaDto);
        }
        return qna;
    }

    public boolean deleteQna(Long id) {
        Optional<CustomServiceQna> qna = customServiceQnaRepository.findById(id);
        if (qna.isPresent()){
            customServiceQnaRepository.delete(qna.get());
            return true;
        }else {
            return false;
        }
    }
    public boolean deleteQnaAllId(List<Long> id) {
        List<CustomServiceQna> qnaList = customServiceQnaRepository.findAllById(id);
        if (!qnaList.isEmpty()){
            for (int i=0;i<qnaList.size();i++){
                customServiceQnaRepository.delete(qnaList.get(i));
            }
            return true;
        }
        return true;
    }
}
