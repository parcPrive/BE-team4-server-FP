package com.kj.codyBoards.codyBoardComments;


import com.kj.codyBoards.codyBoardComments.dto.CodyBoardCommentInputDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardCommentReturnDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardReplyInputDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardReplyReturnDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/codyboard")
@RequiredArgsConstructor
@Getter
@Slf4j
public class CodyBoardCommentsController {
    private final CodyBoardCommentsService codyBoardCommentsService;
    @PostMapping("/insertcomment")
    @ResponseBody
    private CodyBoardCommentReturnDto insertComment(
            @ModelAttribute CodyBoardCommentInputDto codyboardCommentInputDto
            ){
        log.info("머가 들어올까요?? ===>> {}", codyboardCommentInputDto);
        CodyBoardCommentReturnDto resultCodyBoardComment =  codyBoardCommentsService.insertCodyBaordComments(codyboardCommentInputDto);
        log.info("너는 뭐나오니??? ?? ===>> {}", resultCodyBoardComment);
        return resultCodyBoardComment;
    }

    @PostMapping("/insertreply")
    @ResponseBody
    private CodyBoardReplyReturnDto insertReply(
            @ModelAttribute CodyBoardReplyInputDto codyBoardReplyInputDto
    ){
        log.info("대댓글 정보들 ===>>> {}", codyBoardReplyInputDto);
        CodyBoardReplyReturnDto codyBoardReply = codyBoardCommentsService.insertCodyBoardReply(codyBoardReplyInputDto);
        return codyBoardReply;
    }

}
