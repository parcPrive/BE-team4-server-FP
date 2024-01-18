package com.kj.member.dto;

import com.kj.member.entity.Member;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileDto {
    private Member member;
}
