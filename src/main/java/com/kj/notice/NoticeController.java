package com.kj.notice;

import com.kj.faq.entity.FaqBoard;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.dto.NoticeDto;
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
@RequiredArgsConstructor
@RequestMapping("/cs/notice")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;
    private int paginationSize=5;
    @GetMapping("/list")
    public String noticeList(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page){
        Page<Notice> pagination = noticeService.noticeList(page);
        List<Notice> noticeList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("noticeList",noticeList);
        return "/notice/noticeList";
    }
    @GetMapping("/insert")
    public String insertNotice(){
        return "/notice/insertNotice";
    }

    @PostMapping("/insert")
    public String insertProcessNotice(@ModelAttribute NoticeDto noticeDto,
                                      @AuthenticationPrincipal CustomUserDetails customUserDetails){
        noticeService.insertNotice(noticeDto,customUserDetails);
        return "redirect:/cs/notice/list";
    }


    @GetMapping("/view/{id}/{no}")
    public String viewNotice(@PathVariable Long id,
                             @PathVariable int no,Model model,@RequestParam(value = "page", required = true, defaultValue = "0") int page){
        List<Notice> noticeList = noticeService.findByBetweenNum(no);
        Page<Notice> pagination = noticeService.noticeList(page);
        Notice noticeInfo = noticeService.findById(id);
        model.addAttribute("noticeInfo",noticeInfo);
        model.addAttribute("pagination",pagination);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("no",no);
        return "/notice/viewNotice";
    }
}
