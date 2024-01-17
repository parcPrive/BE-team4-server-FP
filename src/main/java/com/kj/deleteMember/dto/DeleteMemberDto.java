package com.kj.deleteMember.dto;

import com.kj.deleteMember.entity.DeleteMember;
import com.kj.log.dto.LogDto;
import com.kj.log.entity.Log;
import com.kj.member.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteMemberDto {
    private Long id;
    private LocalDateTime deleteDate;

    public static DeleteMember toEntity(DeleteMemberDto logDto, Member member){
        return DeleteMember.builder()
                .id(logDto.id)
                .member(member)
                .deleteDate(LocalDateTime.now())
                .build();
    }

}
