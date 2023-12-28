package com.kj.member.dto;

import com.kj.member.entity.Member;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@RequiredArgsConstructor
public class MemberDto {

    private Long id;

    private String userId;

    private String password;

    private String userName;

    private String nickName;
    private int gender;

    private String address;

    private String addressDetail;

    private String postCode;
    private String email;

    private String mobile;
    private String phone;
    private String profile;
    private LocalDateTime registerDate;

    private int levels;

    public static Member toEntity(MemberDto memberDto){

        return Member.builder()
                .id(memberDto.getId())
                .userId(memberDto.getUserId())
                .password(new BCryptPasswordEncoder().encode(memberDto.getPassword()))
                .userName(memberDto.getUserName())
                .nickName(memberDto.getNickName())
                .gender(memberDto.getGender())
                .address(memberDto.getAddress())
                .addressDetail(memberDto.getAddressDetail())
                .postCode(memberDto.getPostCode())
                .email(memberDto.getEmail())
                .mobile(memberDto.getMobile())
                .phone(memberDto.getPhone())
                .profile(memberDto.getProfile())
                .registerDate(memberDto.getRegisterDate())
                .levels(memberDto.getLevels())
                .build();
    }
    public static MemberDto  toDto(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setUserId(member.getUserId());
        memberDto.setPassword(member.getPassword());
        memberDto.setUserName(member.getUserName());
        memberDto.setNickName(member.getNickName());
        memberDto.setGender(member.getGender());
        memberDto.setAddress(member.getAddress());
        memberDto.setAddressDetail(member.getAddressDetail());
        memberDto.setPostCode(member.getPostCode());
        memberDto.setEmail(member.getEmail());
        memberDto.setMobile(member.getMobile());
        memberDto.setPhone(member.getPhone());
        memberDto.setProfile(member.getProfile());
        memberDto.setRegisterDate(member.getRegisterDate());
        memberDto.setLevels(member.getLevels());
        return memberDto;
    }
}
