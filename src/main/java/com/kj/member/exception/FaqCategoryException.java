package com.kj.member.exception;

import com.kj.code.ErrorCode;
import lombok.Getter;

@Getter
public class FaqCategoryException extends RuntimeException{
    private String detailMessage;
    private ErrorCode errorCode;

    public FaqCategoryException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    public FaqCategoryException(String detailMessage) {
        super(detailMessage);
        this.detailMessage = detailMessage;
    }
    public FaqCategoryException(ErrorCode errorCode, String detailMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}