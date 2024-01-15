package com.kj.faq.entity;

import com.kj.utils.BigFaqCategory;
import com.kj.utils.Role;
import com.kj.utils.SmallFaqCategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "faqCategoryId")
    private Long id;
    @Enumerated(EnumType.STRING)
    private BigFaqCategory bigFaqCategory;
    @Enumerated(EnumType.STRING)
    private SmallFaqCategory smallFaqCategory;


}
