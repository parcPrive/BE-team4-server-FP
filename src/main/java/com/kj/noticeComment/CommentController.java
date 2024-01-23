package com.kj.noticeComment;

import com.kj.noticeComment.dto.CommentDto;
import com.kj.noticeComment.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cs/comment")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/insert")
    public String insertComment(@RequestParam Long id, @RequestParam Long memberId , CommentDto commentDto){
        log.info("=={}",id);
        log.info("=={}",memberId);
        commentService.insert(id,memberId,commentDto);
        return "redirect:/cs/notice/list";
    }
}
