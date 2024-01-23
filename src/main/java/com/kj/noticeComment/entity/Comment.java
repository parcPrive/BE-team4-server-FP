package com.kj.noticeComment.entity;

import com.kj.member.entity.Member;
import com.kj.notice.entity.Notice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "noticeComment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String commentContent;
    private LocalDateTime commentDate;
    private int depth;
    @ManyToOne(fetch = FetchType.EAGER)
    private Notice notice;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();

}
