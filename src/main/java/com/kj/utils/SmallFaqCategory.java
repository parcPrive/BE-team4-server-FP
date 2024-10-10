package com.kj.utils;

public enum SmallFaqCategory {
    FAQ00101("가입/인증"),
    FAQ00102("로그인/정보"),
    FAQ00103("탈퇴/기타"),
    FAQ00201("상품/문의"),
    FAQ00202("불량/하자"),
    FAQ00301("주문"),
    FAQ00302("결제수단"),
    FAQ00401("주문"),
    FAQ00402("기타"),
    FAQ00501("교환/반품"),
    FAQ00502("취소/반품(환불)"),
    FAQ00601("혜택"),
    FAQ00602("후기"),
    FAQ00603("고객센터");

    private final String smallFaqCategory;

    SmallFaqCategory(String smallFaqCategory){
        this.smallFaqCategory = smallFaqCategory;
    }
    public String  getValue(){

        return smallFaqCategory;
    }
}
