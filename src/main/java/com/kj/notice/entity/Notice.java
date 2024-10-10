package com.kj.notice.entity;

import com.kj.member.entity.Member;
import com.kj.notice.dto.NoticeDto;
import com.kj.noticeComment.entity.Comment;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noticeTitle;
    @Column(length = 500)
    private String noticeContent;
    private String noticeCategory;
    private LocalDateTime noticeDate;
    private int noticeView;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;
    @OneToMany( mappedBy = "notice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @Transient
    private Notice prevNotice;
    @Transient
    private Notice nextNotice;

    public Notice update(NoticeDto noticeDto) {
        this.noticeTitle = noticeDto.getNoticeTitle();
        this.noticeContent = noticeDto.getNoticeContent();
        this.noticeDate = LocalDateTime.now();
        this.noticeCategory = noticeDto.getNoticeCategory();
        return this;
    }

    public Notice updateView(int noticeView) {
        this.noticeView = noticeView;
        return this;
    }
}
