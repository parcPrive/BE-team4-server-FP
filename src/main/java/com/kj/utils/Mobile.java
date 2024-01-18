package com.kj.utils;

public enum Mobile {
    LG("LGU+"),
    KT("KT"),
    SKT("SKT"),
    ALTTEUL_LG("알뜰폰 LG"),
    ALTTEUL_KT("알뜰폰 KT"),
    ALTTEUL_SKT("알뜰폰 SKT");

    private final String mobile;

    Mobile(String mobile){

        this.mobile = mobile;
    }
    public String  getValue(){
        return mobile;
    }
}

