package com.kj.codyBoards.codyBoardComments.dto;

import lombok.Data;

@Data
public class CodyBoardReplyInputDto {
    private Long codyBoardId;
    private String userName;
    private String content;
    private Long commentId;
}
