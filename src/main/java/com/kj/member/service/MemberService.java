package com.kj.member.service;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.MemberDto;
import com.kj.member.dto.UpdateMemberDto;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberDto insertMember(MemberDto memberDto){
        Member member = MemberDto.toEntity(memberDto);
        memberRepository.save(member);
        return memberDto;
    }


    public MemberDto findByMemberId(Long id, CustomUserDetails customUserDetails) {
        if(id==customUserDetails.getLoggedMember().getId()){
            Member findId = memberRepository.findById(id).orElse(null);
            MemberDto memberDto = MemberDto.toDto(findId);
            return memberDto;
        }
            throw new RuntimeException("없는 사람입니다");
    }


    @Transactional
    public Member updateMember(MemberDto memberDto) {
        Optional<Member> member = memberRepository.findById(memberDto.getId());
        if(member.isPresent()){
          return member.get().update(memberDto);
        }
        throw new RuntimeException("없음");
    }


    @Transactional
    public boolean deleteMember(Long id,String password) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
        new UsernameNotFoundException("아이디 존재하지 않습니다."));
        log.info("=={}",member.getPassword());
        log.info("=={}",password);
        if (bCryptPasswordEncoder.matches(password, member.getPassword())){
            memberRepository.delete(member);
            return true;
        }else {
            return false;
        }

    }
}
