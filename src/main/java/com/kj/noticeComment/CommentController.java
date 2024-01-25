package com.kj.noticeComment;

import com.kj.member.dto.CustomUserDetails;
import com.kj.noticeComment.dto.CommentDto;
import com.kj.noticeComment.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/cs/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/insert")
    public String insertComment(@RequestParam Long id, @RequestParam Long memberId ,
                                @RequestParam Long no ,CommentDto commentDto){
        log.info("=={}",id);
        log.info("=={}",memberId);
        Long commentId = commentService.insert(id,memberId,commentDto);
        return "redirect:/cs/notice/view/"+id+"/"+no;
    }
    @PostMapping("/insertReply")
    @ResponseBody
    public Map<String,Object> insertReply(@RequestParam(value = "commentId",required = false) Long commentId,
                                          @RequestParam(value = "noticeId",required = false)Long noticeId
            ,CommentDto commentDto, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("=={}",commentId);
        log.info("=={}",noticeId);
        log.info("=={}",commentDto.getCommentContent());
        Comment comments = commentService.insertReply(commentId,noticeId,commentDto,customUserDetails);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("insert","ok");
        resultMap.put("comments",comments);
        log.info("=======",resultMap);
        return resultMap;
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map<String,Object> deleteComment(@RequestParam(value = "id",required = false) Long id) {
        boolean result = commentService.delete(id);

        Map<String,Object> resultMap = new HashMap<>();
        if (result){
            resultMap.put("isDelete",true);
        }else {
         resultMap.put("isDelete",false) ;
        }
        return resultMap;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map<String,Object> updateComment(@RequestParam(value = "id",required = false) Long id,
                                            CommentDto commentDto) {

        Comment comment = commentService.update(id,commentDto);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isUpdate","ok");
        resultMap.put("comment",comment);
        return resultMap;
    }

}
