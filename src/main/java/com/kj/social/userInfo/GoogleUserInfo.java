package com.kj.social.userInfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class GoogleUserInfo implements SocialUserInfo {
    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return getProvider()+"_"+(String) attributes.get("sub");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String)attributes.get("name");
    }
}
