package com.kj.codyBoards.codyBoard.Repository;

import com.kj.codyBoards.codyBoard.dto.CodyBoardListReturnDto;
import com.kj.codyBoards.codyBoard.dto.CodyBoardSearchCondition;
import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CodyBoardRepositoryCustom {
    PageImpl<CodyBoardListReturnDto> findListCodyBoard(Pageable pageable, CodyBoardSearchCondition codyBoardSearchCondition);
    CodyBoard findByCodyBoard(Long codyBoardId);
}

