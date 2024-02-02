package com.kj.customServiceQna;

import com.kj.customServiceQna.dto.CustomServiceQnaDto;
import com.kj.customServiceQna.entity.CustomServiceQna;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cs/qna")
@RequiredArgsConstructor
@Slf4j
public class CustomServiceQnaController {
    private final CustomServiceQnaService customServiceQnaService;
    private int paginationSize=10;
    @GetMapping("/check/{id}")
    public String checkQna(@PathVariable Long id, Model model){
        model.addAttribute("id",id);
        return "/qna/checkQna";
    }
    @PostMapping("/check")
    public String checkQna(@RequestParam(required = false) Long id, @RequestParam String qnaPassword, Model model){
        Boolean result = customServiceQnaService.qnaLogin(id,qnaPassword);
        if(result){
            return "redirect:/cs/qna/view/"+id;
        }else {
            return "redirect:/cs/qna/check/"+id;
        }
    }
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
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        CustomServiceQna qnaInfo = customServiceQnaService.findById(id);
        model.addAttribute("qnaInfo",qnaInfo);
        return "/qna/updateQna";
    }
    @Transactional
    @PostMapping("/update")
    public String updateProcess(@RequestParam(required = false) Long id, @ModelAttribute CustomServiceQnaDto customServiceQnaDto,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
        customServiceQnaService.findByUpdateId(id,customServiceQnaDto);
        return "redirect:/cs/qna/list";
    }

    @GetMapping("/view/{id}")
    public String viewQna(@PathVariable Long id, Model model){
    log.info("id=={}",id);
        CustomServiceQna qnaInfo = customServiceQnaService.findById(id);
        model.addAttribute("qnaInfo",qnaInfo);
        return "/qna/viewQna";
    }
    @GetMapping("/search")
    public String searchQnaList(Model model,@RequestParam String category,
                             @RequestParam String keyword,@RequestParam String search, @RequestParam(value = "page", required = true, defaultValue = "0") int page){

        Page<CustomServiceQna> pagination = customServiceQnaService.searchQnaList(category,keyword,page);
        List<CustomServiceQna> qnaList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("qnaList",qnaList);
        if (keyword ==""){
            return "redirect:/cs/qna/list";
        }
        return "/qna/qnaList";
    }
    @GetMapping("/searchView/{id}")
    public String searchViewQna(@PathVariable Long id, //QNA ID
                                    Model model
                                   ){
        log.info("id=={}",id);
        CustomServiceQna qnaInfo = customServiceQnaService.findById(id);
        model.addAttribute("qnaInfo",qnaInfo);
        return "/qna/viewQna";

    }
    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> deleteQna(@RequestParam(value = "id",required = false) Long id) {
        boolean result = customServiceQnaService.deleteQna(id);

        Map<String,Object> resultMap = new HashMap<>();
        if (result){
            resultMap.put("isDelete",true);
        }else {
            resultMap.put("isDelete",false) ;
        }
        return resultMap;
    }
    @PostMapping("/deleteAll")
    @ResponseBody
    public Map<String,Object> deleteAllQna(@RequestParam(value = "id") List<Long> id) {
        boolean result = customServiceQnaService.deleteQnaAllId(id);
        Map<String,Object> resultMap = new HashMap<>();
        if (result){
            resultMap.put("isDelete",true);
        }else {
            resultMap.put("isDelete",false) ;
        }
        return resultMap;
    }
    @GetMapping("/member")
    public String qnaMember(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Page<CustomServiceQna> pagination = customServiceQnaService.memberQnaList(customUserDetails.getLoggedMember().getId(),page);
        List<CustomServiceQna> qnaMemberList = pagination.getContent();

        CustomServiceQna answer = customServiceQnaService.memberAnswerInfo(customUserDetails.getLoggedMember().getId());
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("qnaMemberList",qnaMemberList);
        model.addAttribute("answer",answer);
        return "/member/qna";
    }

    @PostMapping("/api/member")
    @ResponseBody
    public Map<String,Object> answerMember(@RequestParam(value = "id") Long id){

        Map<String,Object> resultMap = new HashMap<>();
        CustomServiceQna answer = customServiceQnaService.memberAnswerInfo(id);
        resultMap.put("answer",answer);
        return resultMap;
    }

}
