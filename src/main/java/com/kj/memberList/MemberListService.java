package com.kj.memberList;

import com.kj.jwt.JwtUtil;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberListService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;

    public Page<Member> findAllPageMember(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"registerDate"));
        Page<Member> memberList = memberRepository.findByAll(pageable);
        return memberList;

    }
    public Page<Member> findAllBlackPageMember(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"registerDate"));
        Page<Member> memberList = memberRepository.findByBlack(pageable);
        return memberList;

    }
    public Page<Member> findAllSearchPageMember(String category, String keyword,int page, String black) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "registerDate"));
        if (category.equals("userId")) {
            Page<Member> memberList =black.equals("5") ? memberRepository.findByBlackUserId(keyword, pageable)
                    : memberRepository.findByUserId(keyword, pageable);
            return memberList;
        } else if(category.equals("userName")) {
            Page<Member> memberList =black.equals("5") ? memberRepository.findByBlackUserName(keyword, pageable)
                    : memberRepository.findByUserName(keyword, pageable);
            return memberList;
        }else if(category.equals("nickName")) {
            Page<Member> memberList =black.equals("5") ? memberRepository.findByBlackNickName(keyword, pageable)
                    : memberRepository.findByNickName(keyword, pageable);
            return memberList;
        } else if(category.equals("level")) {
            Page<Member> memberList = memberRepository.findByLevels(keyword, pageable);
            return memberList;
        } else {
            log.info("전체검색");
            Page<Member> memberList =black.equals("5") ? memberRepository.findByBlackAllCategory(keyword, pageable)
                    : memberRepository.findByAllCategory(keyword, pageable);
            return memberList;
        }
    }

    @Transactional
    public Member updateLevel(String level,Long id) {
        int realLevel = 0;
        realLevel = Integer.parseInt(level);
        log.info("level=={}",level);
        log.info("realId=={}",id);
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
            log.info("realId=={}","ㅂㄷㄱㄹㄷㄱㅎㄱㅎ");
            return member.get().updateLevel(level);
        }
        throw new RuntimeException("없음");
    }

    @Transactional
    public Member updateLevelAll(String level, List<Long> id) {
        log.info("=={}",id);
        List<Member> memberList = memberRepository.findLevelAll(id);
        Member member = null;
        for (int i=0;i<memberList.size();i++){
            member=  memberList.get(i).updateLevel(level);
        }
        return member;

    }
   /* @Transactional
    public Member updateLevelAll(String level, Long[] id) {
        Optional<Member> member = memberRepository.findLevelAll(id);
        if(member.isPresent()){
            log.info("realId=={}","ㅂㄷㄱㄹㄷㄱㅎㄱㅎ");
            return member.get().updateLevel(level);
        }
        throw new RuntimeException("없음");
    }*/
}
