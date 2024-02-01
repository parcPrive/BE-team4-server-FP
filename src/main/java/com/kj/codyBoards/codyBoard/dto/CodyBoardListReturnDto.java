package com.kj.codyBoards.codyBoard.dto;

import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CodyBoardListReturnDto {
    private Long id;
    private String codyBoardTitle;
    private String userName;
    private int clickCount;
    private String thumbnail;
    private LocalDateTime createdAt;

    @Builder

    public CodyBoardListReturnDto(CodyBoard codyBoard) {
        this.id = codyBoard.getId();
        this.codyBoardTitle = codyBoard.getCodyBoardTitle();
        this.userName = codyBoard.getMember().getUserName();
        this.thumbnail = codyBoard.getCodyboardImage1();
        this.clickCount = codyBoard.getClickCount();
        this.createdAt = codyBoard.getCreatedAt();
    }
}
