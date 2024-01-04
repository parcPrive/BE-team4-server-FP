package com.kj.config;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((auth)->auth
                        .requestMatchers("/","/member/insert","/member/login","/css/**","/js/**","/images/**","/mail/**","/product/**")
                        .permitAll()
                                .requestMatchers("/member/update","/member/mypage").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                        )
                .formLogin((form)->form
                        .loginPage("/member/login")   // get
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/member/login")  //post
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                .logout((form)->form
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                )
                .csrf((csrf)->  csrf.disable());

        return httpSecurity.build();
    }


    }
