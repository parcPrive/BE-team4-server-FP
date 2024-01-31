package com.kj.codyBoards.codyBoardComments.entity;

import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
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
public class CodyBoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODYBOARD_COMMENT_ID")
    private Long id;
    private String userName;
    private String content;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private CodyBoardComment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<CodyBoardComment> children;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODYBOARD_ID")
    private CodyBoard codyBoard;
}
