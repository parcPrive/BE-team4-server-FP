package com.kj.log.dto;


import com.kj.log.entity.Log;
import com.kj.member.dto.JoinDto;
import com.kj.member.dto.MemberDto;
import com.kj.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogDto {
    Long id;
    LocalDateTime loginDate;

    public static Log toEntity(LogDto logDto, Member member){
        return Log.builder()
                .id(logDto.id)
                .member_id(member)
                .loginDate(LocalDateTime.now())
                .build();
    }
    public static LogDto toDto(Log logMember){
        LogDto logDto = new LogDto();
        logDto.setId(logMember.getId());
        logDto.setLoginDate(LocalDateTime.now());
        return logDto;
    }

}
