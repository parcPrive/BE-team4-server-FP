package com.kj.member.dto;

import com.kj.code.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {
    private ErrorCode errorCode;
    private String errorMessage;
}