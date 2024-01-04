package com.kj.member.service;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        if (findMember.isPresent()){
            return new CustomUserDetails(findMember.get());
        }
        throw new UsernameNotFoundException("아이디 패스워드 확인해주세요");
    }
}
