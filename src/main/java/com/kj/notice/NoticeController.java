package com.kj.notice;

import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.member.dto.CustomUserDetails;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.CommentService;
import com.kj.noticeComment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cs/notice")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;
    private final CommentService commentService;
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
    public String viewNotice(@PathVariable Long id, //공지사항 ID
                             @PathVariable int no,Model model,
                             @RequestParam(value = "page", required = true, defaultValue = "0") int page){
        Page<Notice> pagination = noticeService.viewNoticeList(page,no);
        Notice noticeInfo = noticeService.findByIdPlusView(id);

        List<Comment> commentList = commentService.commentList(id);
        List<Notice> noticeList = pagination.getContent();
        model.addAttribute("noticeInfo",noticeInfo);
        model.addAttribute("pagination",pagination);
        model.addAttribute("noticeList",noticeList);
        model.addAttribute("commentList",commentList);
        model.addAttribute("no",no);
        return "/notice/viewNotice";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        Notice noticeInfo = noticeService.findById(id);
        model.addAttribute("noticeInfo",noticeInfo);
        return "/notice/updateNotice";
    }
    @Transactional
    @PostMapping("/update")
    public String updateProcess(@RequestParam Long id, @ModelAttribute NoticeDto noticeDto,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
        noticeService.updateNotice(noticeDto);
        return "redirect:/cs/notice/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boolean isDelete = noticeService.deleteById(id);
        if(isDelete){
            return "redirect:/cs/notice/list";
        }else {
            return "redirect:/";
        }
    }
}
