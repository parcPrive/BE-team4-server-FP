package com.kj.codyBoards.codyBoardComments.dto;

import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import lombok.Data;

import java.util.List;
@Data
public class CodyBoardCommentsViewDto {
    private CodyBoardComment codyBoardComment;
    private List<CodyBoardComment> codyBoardReply;

    public CodyBoardCommentsViewDto(CodyBoard codyBoard) {
        this.codyBoardComment = codyBoard.getCodyBoardComments().get(0);
        this.codyBoardReply = codyBoard.getCodyBoardComments();
    }
}
