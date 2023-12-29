package com.kj.mail.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MailDto {
    String email;
    String userName;
}
