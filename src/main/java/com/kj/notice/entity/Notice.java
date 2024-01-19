package com.kj.notice.entity;

import com.kj.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String noticeTitle;
    private String noticeContent;
    private String noticeCategory;
    private LocalDateTime noticeDate;
    private int noticeView;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;
}
