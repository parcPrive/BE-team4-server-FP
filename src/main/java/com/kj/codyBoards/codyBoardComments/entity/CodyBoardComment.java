package com.kj.codyBoards.codyBoardComments.entity;

import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardCommentInputDto;
import com.kj.codyBoards.codyBoardComments.dto.CodyBoardReplyInputDto;
import com.kj.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodyBoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODYBOARD_COMMENT_ID")
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private int sortNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private CodyBoardComment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<CodyBoardComment> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODYBOARD_ID")
    private CodyBoard codyBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Builder
    public CodyBoardComment(CodyBoardCommentInputDto codyboardCommentInputDto,int sortNum ,CodyBoard codyBoard, Member member) {
        this.content = codyboardCommentInputDto.getContent();
        this.createdAt = LocalDateTime.now();
        this.sortNum = sortNum;
        this.codyBoard = codyBoard;
        this.member = member;
    }

    @Builder
    public CodyBoardComment(CodyBoardReplyInputDto codyBoardReplyInputDto, CodyBoardComment codyBoardComment, CodyBoard codyBoard,  Member member) {
        this.content = codyBoardReplyInputDto.getContent();
        this.createdAt = LocalDateTime.now();
        this.sortNum = codyBoardComment.getSortNum();
        this.parent = codyBoardComment;
        this.codyBoard = codyBoard;
        this.member = member;
    }
}
