package com.kj.codyBoards.codyBoard.Repository;

import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodyBoardRepository extends JpaRepository<CodyBoard, Long> {
}
