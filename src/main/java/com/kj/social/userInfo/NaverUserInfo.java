package com.kj.social.userInfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverUserInfo implements SocialUserInfo {
    private final Map<String , Object> attibutes;
    @Override
    public String getProvider() {
        return "naver";
    }
    @Override
    public String getProviderId() {
        return getProvider()+"_"+(String)attibutes.get("id");
    }
    @Override
    public String getEmail() {
        return (String)attibutes.get("email");
    }
    @Override
    public String getName() {
        return (String)attibutes.get("name");
    }
}
