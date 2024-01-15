package com.kj.faq;

import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.faq.repository.FaqCategoryRepository;
import com.kj.faq.service.FaqBoardService;
import com.kj.faq.service.FaqCategoryService;
import com.kj.log.entity.Log;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import com.kj.utils.BigFaqCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/cs")
@RequiredArgsConstructor
@Slf4j
public class FaqController {
    private final FaqCategoryService faqCategoryService;
    private final FaqBoardService faqBoardService;
    private int paginationSize=5;


    @GetMapping("/insertFaq")
    public String insertFaq(Model model){

        return "/customService/insertFaq";
    }

    @Transactional
    @PostMapping("/insertFaq")
    public String insertFaqProcess(@ModelAttribute FaqBoardDto faqBoardDto,@ModelAttribute FaqCategoryDto faqCategoryDto,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("=={}",faqCategoryDto.getSmallFaqCategory());
        FaqCategory faqCategory = faqCategoryService.findByFaqCateGoryId(faqCategoryDto);
        faqBoardService.insertFaqBoard(faqBoardDto,customUserDetails,faqCategory);
        return "redirect:/cs/faq";
    }

    @GetMapping("/insertFaqCategory")
    public String insertFaqCategory(Model model){

        return "/customService/insertFaqCategory";
    }
    @PostMapping("/insertFaqCategory")
    public String insertFaqCategoryProcess(Model model, FaqCategoryDto faqCategoryDto){
        faqCategoryService.insertCategory(faqCategoryDto);
        return "/customService/insertFaqCategory";
    }

    @GetMapping("/faq")
    public String listFaq(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page) {
        Page<FaqBoard> pagination = faqBoardService.findAllPageFaq(page);
        List<FaqBoard> faqBoardList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("faqBoardList",faqBoardList);
        return "/customService/faqList";
    }
    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        FaqBoard faqBoardInfo = faqBoardService.findById(id);
        model.addAttribute("faqBoardInfo",faqBoardInfo);
        return "/customService/viewFaq";
    }

    @GetMapping("/updateFaq/{id}")
    public String update(@PathVariable Long id, Model model) {
        FaqBoard faqBoardInfo = faqBoardService.findById(id);
        model.addAttribute("faqBoardInfo",faqBoardInfo);
        return "/customService/updateFaq";
    }
    @Transactional
    @PostMapping("/updateFaq")
    public String updateProcess(@ModelAttribute FaqBoardDto faqBoardDto,@ModelAttribute FaqCategoryDto faqCategoryDto,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("=={}",faqCategoryDto.getSmallFaqCategory());
        log.info("=={}",faqCategoryDto.getBigFaqCategory());
        FaqCategory faqCategory = faqCategoryService.findByFaqCateGoryId(faqCategoryDto);
        faqBoardService.updateFaqBoard(faqBoardDto,faqCategory);
        return "/customService/faqList";
    }
    @GetMapping("/deleteFaq/{id}")
    public String delete(@PathVariable Long id) {
        log.info("=={}",id);
        boolean isDelete = faqBoardService.deleteById(id);
        if(isDelete){
            return "redirect:/cs/faq";
        }else {
        return "redirect:/";

        }
    }
}
