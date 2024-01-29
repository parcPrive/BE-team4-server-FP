package com.kj.notice;

import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import com.kj.notice.dto.NoticeDto;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.CommentService;
import com.kj.noticeComment.entity.Comment;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    @GetMapping("/search")
    public String searchList(Model model,@RequestParam String category,
                             @RequestParam String keyword,@RequestParam String search, @RequestParam(value = "page", required = true, defaultValue = "0") int page){

        Page<Notice> pagination = noticeService.searchList(category,keyword,page);
        List<Notice> noticeList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("noticeList",noticeList);
        if (keyword ==""){
            return "redirect:/cs/notice/list";
        }
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
    @GetMapping("/searchView/{id}/{category}/{keyword}/{search}")
    public String searchViewNotice(@PathVariable Long id, //공지사항 ID
                              @PathVariable String category,
                                   @PathVariable String keyword,
                                   @PathVariable String search,Model model,
                                   @RequestParam(value = "page", required = true, defaultValue = "0") int page
            , HttpServletRequest request, HttpServletResponse response){

        Notice noticeInfo = noticeService.findByIdSearchPlusView(id,category,keyword,request,response);
        List<Comment> commentList = commentService.commentList(id);
        model.addAttribute("noticeInfo",noticeInfo);
        model.addAttribute("commentList",commentList);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("search",search);
        return "/notice/viewNotice";
    }

    @GetMapping("/view/{id}")
    public String viewNotice(@PathVariable Long id, Model model,
                             @RequestParam(value = "page", required = true, defaultValue = "0") int page,
                             HttpServletRequest request, HttpServletResponse response){
        Notice noticeInfo = noticeService.findByIdPlusView(id,request,response);
        List<Comment> commentList = commentService.commentList(id);
        model.addAttribute("noticeInfo",noticeInfo);
        model.addAttribute("commentList",commentList);
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
