package com.kj.faq.service;

import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.faq.repository.FaqBoardRepository;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqBoardService {
    private final FaqBoardRepository faqBoardRepository;

    public void insertFaqBoard(FaqBoardDto faqBoardDto, CustomUserDetails customUserDetails, FaqCategory faqCategory){
        FaqBoard insetDBFaqBoard = FaqBoardDto.toEntity(faqBoardDto, customUserDetails, faqCategory);
        FaqBoard faqBoard = faqBoardRepository.save(insetDBFaqBoard);
    }

    public Page<FaqBoard> findAllPageFaq(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"faqDate"));
        Page<FaqBoard> faqBoardList = faqBoardRepository.findByAll(pageable);
        return faqBoardList;
    }

    public FaqBoard findById(Long id) {
       Optional<FaqBoard> faqBoard = faqBoardRepository.findById(id);
       if (faqBoard.isPresent()){
           return faqBoard.get();
       }
       throw new RuntimeException("존재하지 않습니다.");
    }

    public boolean deleteById(Long id) {
        Optional<FaqBoard> faqBoard = faqBoardRepository.findById(id);
        if (faqBoard.isPresent()){
            faqBoardRepository.delete(faqBoard.get());
            return true;
        }
        return false;
    }

    public FaqBoard updateFaqBoard(FaqBoardDto faqBoardDto, FaqCategory faqCategory) {
        Optional<FaqBoard> faqBoard = faqBoardRepository.findById(faqBoardDto.getId());
        if (faqBoard.isPresent()){
            return faqBoard.get().update(faqBoardDto,faqCategory);
        }
        throw new RuntimeException("존재하지 않습니다.");
    }
}
