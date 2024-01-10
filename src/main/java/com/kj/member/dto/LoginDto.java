package com.kj.member.dto;

import com.kj.member.entity.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {
    private String userId;
    private String password;
    private String check;

    public static Member toEntity(LoginDto loginDto){
        return Member.builder()
                .userId(loginDto.getUserId())
                .password(loginDto.getPassword())
                .build();
    }
    public static LoginDto toDto(Member member){
        LoginDto loginDto = new LoginDto();
        loginDto.setUserId(member.getUserId());
        loginDto.setPassword(member.getPassword());
        return loginDto;
    }
}
