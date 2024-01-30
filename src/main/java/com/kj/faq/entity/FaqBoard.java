package com.kj.faq.entity;

import com.kj.faq.dto.FaqBoardDto;
import com.kj.faq.dto.FaqCategoryDto;
import com.kj.member.dto.MemberDto;
import com.kj.member.entity.Member;
import com.kj.productCategory.entity.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class FaqBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faqBoardId")
    private Long id;
    private String faqTitle;
    private String faqContent;
    private LocalDateTime faqDate;
    @ManyToOne
    @JoinColumn(name = "faqCategoryId")
    private FaqCategory faqCategory;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member writer;

    public FaqBoard update(FaqBoardDto faqBoardDto, FaqCategory faqCategory){
        this.faqTitle = faqBoardDto.getFaqTitle();
        this.faqContent = faqBoardDto.getFaqContent();
        this.faqCategory = faqCategory;
        return this;
    }
}
