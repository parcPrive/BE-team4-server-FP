package com.kj.codyBoards.codyBoardLike.entity;

import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodyBoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODYBOARD_LIKE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CODYBOARD_ID")
    private CodyBoard codyBoard;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
