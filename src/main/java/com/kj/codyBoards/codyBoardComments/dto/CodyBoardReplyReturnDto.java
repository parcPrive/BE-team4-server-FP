package com.kj.codyBoards.codyBoardComments.dto;

import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CodyBoardReplyReturnDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String userName;

    @Builder
    public CodyBoardReplyReturnDto(CodyBoardComment codyBoardComment) {
        this.id = codyBoardComment.getId();
        this.content = codyBoardComment.getContent();
        this.createdAt = codyBoardComment.getCreatedAt();
        this.userName = codyBoardComment.getMember().getUserName();
    }
}
