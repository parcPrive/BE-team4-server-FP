package com.kj.customServiceQna.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.entity.FaqBoard;
import com.kj.faq.entity.FaqCategory;
import com.kj.member.entity.Member;
import com.kj.noticeComment.entity.Comment;
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
public class CustomServiceQna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String qnaCategory;
    private String qnaTitle;
    private String qnaContent;
    private LocalDateTime qnaDate;
    private String qnaStatus;
    private int qnaSecret;
    private String qnaPassword;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private CustomServiceQna parent;
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @JsonIgnore
    private List<CustomServiceQna> children = new ArrayList<>();

    public CustomServiceQna update(){
        this.qnaStatus = "답변완료";
        return this;
    }

}
