package com.kj.faq.entity;

import com.kj.noticeComment.entity.Comment;
import com.kj.utils.BigFaqCategory;
import com.kj.utils.Role;
import com.kj.utils.SmallFaqCategory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faqCategoryId")
    private Long id;
    @Enumerated(EnumType.STRING)
    private BigFaqCategory bigFaqCategory;
    @Enumerated(EnumType.STRING)
    private SmallFaqCategory smallFaqCategory;
    @OneToMany( mappedBy = "faqCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FaqBoard> faqBoards;

}
