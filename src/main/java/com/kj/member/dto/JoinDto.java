package com.kj.member.dto;

import com.kj.member.entity.Member;
import com.kj.utils.Mobile;
import com.kj.utils.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JoinDto {
    private Long id;
    @NotBlank(message = "비어있으면 안됩니다.")
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String userName;
    @NotBlank
    private String nickName;
    private int gender;
    private String address;
    private String addressDetail;
    private String postCode;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String mobile;
    @NotBlank(message = "빈값이면 안됩니다.")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$",message = "ㅇㅇㅇ")
    private String phone;
    private LocalDateTime registerDate;

    public static Member toEntity(JoinDto joinDto){

        return Member.builder()
                .id(joinDto.getId())
                .userId(joinDto.getUserId())
                .password(new BCryptPasswordEncoder().encode(joinDto.getPassword()))
                .userName(joinDto.getUserName())
                .nickName(joinDto.getNickName())
                .gender(joinDto.getGender())
                .address(joinDto.getAddress())
                .addressDetail(joinDto.getAddressDetail())
                .postCode(joinDto.getPostCode())
                .email(joinDto.getEmail())
                .mobile(Mobile.valueOf(joinDto.getMobile()))
                .phone(joinDto.getPhone())
                .levels("0")
                .registerDate(LocalDateTime.now())
                .role(Role.ADMIN)
                .build();
    }
}
