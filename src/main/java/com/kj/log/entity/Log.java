package com.kj.log.entity;

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
@Table(name = "LogMember")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    /*@Column(name = "log_id")*/
    Long id;
    LocalDateTime loginDate;
    LocalDateTime blackDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member_id;

}
