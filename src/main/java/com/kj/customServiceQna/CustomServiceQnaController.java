package com.kj.customServiceQna;

import com.kj.customServiceQna.dto.CustomServiceQnaDto;
import com.kj.customServiceQna.entity.CustomServiceQna;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cs/qna")
@RequiredArgsConstructor
@Slf4j
public class CustomServiceQnaController {
    private final CustomServiceQnaService customServiceQnaService;
    private int paginationSize=10;
    @GetMapping("/insert")
    public String insertQna(){
        return "/qna/insertQna";
    }
    @PostMapping("/insert")
    public String insertProcessQna(@ModelAttribute CustomServiceQnaDto customServiceQnaDto,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails){
        customServiceQnaService.insertQna(customServiceQnaDto, customUserDetails);
        return "redirect:/cs/qna/list";
    }
    @PostMapping("/answer")
    public String answerProcessQna(@RequestParam Long qnaId, @ModelAttribute CustomServiceQnaDto customServiceQnaDto,
                                   @AuthenticationPrincipal CustomUserDetails customUserDetails){
        customServiceQnaService.answerQna(qnaId,customServiceQnaDto, customUserDetails);
        return "redirect:/cs/qna/list";
    }

    @GetMapping("/list")
    public String qnaList(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page){
        Page<CustomServiceQna> pagination = customServiceQnaService.qnaList(page);
        List<CustomServiceQna> qnaList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("qnaList",qnaList);
        return "/qna/qnaList";
    }
    @GetMapping("/update")
    public String updateQna(){
        return "/qna/updateQna";
    }
    @GetMapping("/view/{id}")
    public String viewQna(@PathVariable Long id, Model model){
    log.info("id=={}",id);
        CustomServiceQna qnaInfo = customServiceQnaService.findById(id);
        model.addAttribute("qnaInfo",qnaInfo);
        return "/qna/viewQna";
    }

}
