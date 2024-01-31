package com.kj.config;

import com.kj.jwt.JwtAuthFilter;
import com.kj.jwt.JwtUtil;
import com.kj.member.service.CustomUserDetailService;
import com.kj.social.OAuth2DetailService;
import com.kj.social.handler.OAuth2LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final OAuth2DetailService oAuth2DetailService;
    private final JwtUtil jwtUtil;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                authorizeHttpRequests((auth) -> auth

                        .requestMatchers("/", "/member/insert","/member/logout", "/member/nickNameCheck",
                                "/member/login", "/css/**", "/js/**", "/images/**", "/mail/**", "/product/**","/productpayment/**","/admin/**","/codyboard/**")
                        .permitAll()
                        .requestMatchers("/member/update", "/member/mypage").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()

                )


                //베이직 인증 방식 disable
                .httpBasic((auth)->auth.disable())
                //세션설정 NEVER가 아닌 STATELESS로 해주면 세션이 유지되지 않아 oAuth2User가 null이 된다.
                .sessionManagement((session)->session
                        .sessionCreationPolicy(SessionCreationPolicy.NEVER))

                //.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .formLogin((form)->form.disable()
                        /*.loginPage("/member/login")   // get
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/member/login")  //post
                        .defaultSuccessUrl("/",true)
                        .permitAll()*/
                )
                .logout((form)->form
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .deleteCookies("Authorization","JSESSIONID")
                        .invalidateHttpSession(true)
                )
                //소셜 로그인
                .oauth2Login(auth->auth
                        .loginPage("/member/login")
                        .defaultSuccessUrl("/")
                        .successHandler(oAuth2LoginSuccessHandler)
                        .userInfoEndpoint(userInf ->userInf.userService(oAuth2DetailService)
                        )
                )
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .csrf((csrf)->  csrf.disable());


        return httpSecurity.build();
    }


    }
