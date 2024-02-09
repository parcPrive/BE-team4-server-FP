package com.kj.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kj.deleteMember.entity.DeleteMember;
import com.kj.faq.entity.FaqBoard;
import com.kj.log.entity.Log;
import com.kj.member.dto.MemberDto;
import com.kj.member.dto.UpdateMemberDto;

import com.kj.notice.entity.Notice;
import com.kj.products.productPayment.entity.ProductPayment;
import com.kj.utils.Mobile;
import com.kj.utils.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Builder
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userId;
    private String password;
    private String userName;
    @Column(unique = true)
    private String nickName;
    private int gender;
    private String address;
    private String addressDetail;
    private String postCode;
    private String email;
    @Enumerated(EnumType.STRING)
    private Mobile mobile;
    private String phone;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime registerDate;
    private String levels;


    @OneToMany(mappedBy = "member_id", cascade = CascadeType .REMOVE)
    @JsonIgnore
    List<Log> logList = new ArrayList<>();
    @OneToMany(mappedBy = "writer", cascade = CascadeType .REMOVE)
    @JsonIgnore
    List<FaqBoard> faqBoardList = new ArrayList<>();
    @OneToMany(mappedBy = "writer", cascade = CascadeType .REMOVE)
    @JsonIgnore
    List<Notice> noticeList = new ArrayList<>();
    @OneToMany(mappedBy = "member", cascade = CascadeType .REMOVE)
    @JsonIgnore
    List<DeleteMember> deleteMemberList = new ArrayList<>();
    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<ProductPayment> paymentList = new ArrayList<>();


    public Member update(MemberDto memberDto){
        this.email = memberDto.getEmail();
        this.nickName = memberDto.getNickName();
        this.address = memberDto.getAddress();
        this.addressDetail = memberDto.getAddressDetail();
        this.postCode = memberDto.getPostCode();
        this.phone = memberDto.getPhone();
        this.mobile = Mobile.valueOf(memberDto.getMobile());
        return this;
    }
    public Member updateLevel(String levels){

        this.levels = levels;
        return this;
    }
    public void updatePassword(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.password = bCryptPasswordEncoder.encode(password);
    }
    public Member updateProfile(String profileImageUrl){
        this.profileImageUrl = profileImageUrl;
        return this;
    }
}
