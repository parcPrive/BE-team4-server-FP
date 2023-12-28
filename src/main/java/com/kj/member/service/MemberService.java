package com.kj.member.service;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.MemberDto;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberDto join(MemberDto memberDto){
        Member member = MemberDto.toEntity(memberDto);
        memberRepository.save(member);
        return memberDto;
    }


    public MemberDto findMemberInfo(Long id, CustomUserDetails customUserDetails) {
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


        }
        throw new RuntimeException("없음");
    }
}
