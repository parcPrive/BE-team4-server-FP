package com.kj.codyBoards.codyBoard.entiry;

import com.kj.codyBoards.codyBoard.dto.CodyBoardInputDto;
import com.kj.codyBoards.codyBoardComments.entity.CodyBoardComment;
import com.kj.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodyBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODYBOARD_ID")
    private Long id;
    private String codyBoardTitle;
    private String content;
    private int clickCount = 0;
    private LocalDateTime createdAt;
    private String codyBoardImageBucket;
    private String codyboardImage1;
    private String codyboardImage2;

    @OneToMany(mappedBy = "codyBoard", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<CodyBoardComment> codyBoardComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public CodyBoard(CodyBoardInputDto codyBoardInputDto, List<String> codyimageUrls, Member member) {
        this.codyBoardTitle = codyBoardInputDto.getCodyBoardTitle();
        this.content = codyBoardInputDto.getContent();
        this.createdAt = LocalDateTime.now();
        this.codyBoardImageBucket = codyBoardInputDto.getCodyBoardBucket();
        this.codyboardImage1 = codyimageUrls.get(0);
        this.codyboardImage2 = codyimageUrls.get(1);
        this.member = member;
    }
}
