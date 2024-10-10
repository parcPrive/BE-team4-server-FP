package com.kj.member.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModalDto {
    private String isState;
    private String title;
    private String msg;
}