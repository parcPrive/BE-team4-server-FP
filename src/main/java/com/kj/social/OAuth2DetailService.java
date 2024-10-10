package com.kj.social;

import com.kj.jwt.JwtUtil;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.social.userInfo.GoogleUserInfo;
import com.kj.social.userInfo.KakaoUserInfo;
import com.kj.social.userInfo.NaverUserInfo;
import com.kj.social.userInfo.SocialUserInfo;
import com.kj.utils.Role;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2DetailService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String,Object> oAuth2UserInfo =(Map)oAuth2User.getAttributes();
        log.info("oAuth2User.getAuthorities()==={}",oAuth2User.getAttributes());
        log.info("==={}",userRequest.getClientRegistration().getRegistrationId());

        SocialUserInfo socialUserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();
        if (provider.equals("google")){
            socialUserInfo = new GoogleUserInfo(oAuth2UserInfo);
        }
        else if (provider.equals("naver")){
            Map<String,Object> naverResponse = (Map)oAuth2UserInfo.get("response");
            socialUserInfo = new NaverUserInfo(naverResponse);
        }else if (provider.equals("kakao")){
            socialUserInfo = new KakaoUserInfo(oAuth2UserInfo);
        }

        String email = socialUserInfo.getEmail();
        String userName =socialUserInfo.getName();
        String userId = socialUserInfo.getProviderId();
        String role = "USER_ROLE";
        String password = bCryptPasswordEncoder.encode(UUID.randomUUID().toString());

        Member returnMember= null;

        Optional<Member> findId = memberRepository.findByUserId(userId);
        //맴버가 존재하면 찾고
        if(findId.isPresent()){
            returnMember= findId.get();
            //없으면 DB에 저장
        }else {
            returnMember = Member.builder()
                    .userId(userId)
                    .password(password)
                    .role(Role.USER)
                    .userName(userName)
                    .email(email)
                    .build();
            memberRepository.save(returnMember);  //오류는 계속 로그인하면 계속 저장이 되어서 무결성 제약에 걸린다.
        }

        log.info("=={}",new CustomUserDetails(returnMember,oAuth2User.getAttributes()).getName());
        return new CustomUserDetails(returnMember,oAuth2User.getAttributes());
    }

}
