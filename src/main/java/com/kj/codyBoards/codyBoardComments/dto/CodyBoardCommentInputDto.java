package com.kj.codyBoards.codyBoardComments.dto;

import lombok.Data;

@Data
public class CodyBoardCommentInputDto {
    private Long codyBoardId;
    private String userName;
    private String content;
}
