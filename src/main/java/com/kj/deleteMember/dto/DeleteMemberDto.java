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
    private String preLevel;

    public static DeleteMember toEntity(DeleteMemberDto dto, Member member){
        return DeleteMember.builder()
                .id(dto.id)
                .preLevel(dto.getPreLevel())
                .member(member)
                .deleteDate(LocalDateTime.now())
                .build();
    }

}
