package com.kj.utils;

public enum BigFaqCategory {
        FAQ001("회원정보"),
        FAQ002("상품확인"),
        FAQ003("주문/결제"),
        FAQ004("배송"),
        FAQ005("교환/취소(반품)"),
        FAQ006("서비스");


    private final String bigFaqCategory;



    BigFaqCategory(String bigFaqCategory){
        this.bigFaqCategory = bigFaqCategory;
    }
    public String  getValue(){

        return bigFaqCategory;
    }
}
