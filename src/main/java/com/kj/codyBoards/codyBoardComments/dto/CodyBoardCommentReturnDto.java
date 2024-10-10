package com.kj.codyBoards.codyBoardComments.dto;

import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CodyBoardCommentReturnDto {
    private Long id;
    private String userName;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public CodyBoardCommentReturnDto(CodyBoardComment codyBoardComment) {
        this.id = codyBoardComment.getId();
        this.userName = codyBoardComment.getMember().getUserName();
        this.content = codyBoardComment.getContent();
        this.createdAt = codyBoardComment.getCreatedAt();
    }
}
