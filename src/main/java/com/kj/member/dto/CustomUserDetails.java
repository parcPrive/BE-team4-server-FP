package com.kj.member.dto;

import com.kj.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter
public class CustomUserDetails implements UserDetails, OAuth2User {
    private Member loggedMember;
    private Map<String,Object> attribute;

    public CustomUserDetails(Member loggedMember){

        this.loggedMember = loggedMember;
    }
    public CustomUserDetails(Member loggedMember,Map<String,Object> attribute){
        this.loggedMember =loggedMember;
        this.attribute = attribute;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attribute;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                if (loggedMember.getRole()==null){ // 롤값이 널이면 널을 던진다.
                    return null;
                }
                return loggedMember.getRole().getValue();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return loggedMember.getPassword();
    }

    @Override
    public String getUsername() {
        return loggedMember.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return (String) attribute.get("name");
    }
}
