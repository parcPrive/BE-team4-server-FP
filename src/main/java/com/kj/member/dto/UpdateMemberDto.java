package com.kj.member.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateMemberDto {
    private String email;
    private String nickName;

    public UpdateMemberDto(String email){
        this.email = email;
    }
}
