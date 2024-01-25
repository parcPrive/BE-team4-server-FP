package com.kj.noticeComment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kj.member.entity.Member;
import com.kj.notice.entity.Notice;
import com.kj.noticeComment.dto.CommentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "noticeComment")
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String commentContent;
    private LocalDateTime commentDate;
    private int depth;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"comments"})
    private Notice notice;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Comment parent;
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @JsonIgnore
    private List<Comment> children = new ArrayList<>();

    public Comment update(CommentDto commentDto){
        this.commentContent = commentDto.getCommentContent();
        this.commentDate = LocalDateTime.now();
        return this;
    }

}
