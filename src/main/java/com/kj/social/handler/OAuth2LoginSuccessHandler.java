package com.kj.social.handler;

import com.kj.jwt.JwtUtil;
import com.kj.member.dto.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = jwtUtil.createToken(customUserDetails.getUsername(),customUserDetails.getLoggedMember().getRole());
        Cookie cookie = new Cookie(jwtUtil.AUTHORIZATION_HEADER,accessToken);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7일 동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
