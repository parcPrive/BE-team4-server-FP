package com.kj.member.entity;

import com.kj.member.dto.MemberDto;
import com.kj.member.dto.UpdateMemberDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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


    public Member update(MemberDto memberDto){
        this.email = memberDto.getEmail();
        this.userName = memberDto.getUserName();
        return this;
    }






}
