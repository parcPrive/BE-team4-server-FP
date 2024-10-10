package com.kj.deleteMember.entity;

import com.kj.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deleteId")
    private Long id;
    private LocalDateTime deleteDate;
    private String preLevel;
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;
}
