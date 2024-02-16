package com.kj.memberList;

import com.kj.deleteMember.dto.DeleteMemberDto;
import com.kj.deleteMember.entity.DeleteMember;
import com.kj.deleteMember.repository.DeleteMemberRepository;
import com.kj.jwt.JwtUtil;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import com.kj.products.productPayment.entity.ProductPayment;
import com.kj.products.productPayment.repository.ProductPaymentRepository;
import com.kj.products.productPayment.repository.ProductPaymentRepositoryImpl;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberListService {
    private final MemberRepository memberRepository;
    private final DeleteMemberRepository deleteMemberRepository;
    private final ProductPaymentRepository productPaymentRepository;


    public Page<Member> findAllPageMember(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"registerDate"));
        Page<Member> memberList = memberRepository.findByAll(pageable);
        return memberList;
    }

    @Transactional
    public void pay(String nickname) {
        List<Member> memberList = memberRepository.findByAllSize();
        Member findMember = memberRepository.findByNickName(nickname).orElseThrow();
        if (findMember.getPaymentList().size() > 0) {
            int sum = productPaymentRepository.sumProductPaidPriceByUserNickName(findMember.getNickName()) -
                    productPaymentRepository.sumProductRefundPriceByUserNickName(findMember.getNickName());
            log.info("합계=={}", sum);
            if(sum==0){
                findMember.updateLevel("0");
            } else if (sum > 0 && sum <= 1000) {
                findMember.updateLevel("1");
            } else if (sum > 1000 && sum <= 2000) {
                findMember.updateLevel("2");
            } else if (sum > 2000 && sum <= 3000) {
                findMember.updateLevel("3");
            } else if (sum > 3000 && sum <= 4000) {
                findMember.updateLevel("4");
            }
        } else {
            findMember.updateLevel("0");
        }
       /* for (Member member : memberList) {
            if (member.getPaymentList().size() > 0) {
                int sum = productPaymentRepository.sumProductPaidPriceByUserNickName(member.getNickName()) -
                productPaymentRepository.sumProductRefundPriceByUserNickName(member.getNickName());
                log.info("합계=={}", sum);
                if (sum > 0 && sum <= 1000) {
                    member.updateLevel("1");
                } else if (sum > 1000 && sum <= 2000) {
                    member.updateLevel("2");
                } else if (sum > 2000 && sum <= 3000) {
                    member.updateLevel("3");
                } else if (sum > 3000 && sum <= 4000) {
                    member.updateLevel("4");
                }
            } else {
                member.updateLevel("0");
            }
        }*/
    }

    public List<Member> findAllPageMember() {
        List<Member> memberList = memberRepository.findByAllSize();
        return memberList;
    }

    public List<Member> findByRegisterDate(){
        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0)); //어제 00:00:00
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59)); //오늘 23:59:59
        List<Member> memberList = memberRepository.findByRegisterDateBetween(startDatetime,endDatetime);
        return memberList;
    }
    public Page<Member> findAllBlackPageMember(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"registerDate"));
        Page<Member> memberList = memberRepository.findByBlack(pageable);
        return memberList;
    }
    public Page<Member> findAllDeletePageMember(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by(Sort.Direction.DESC,"register_Date"));
        Page<Member> memberList = memberRepository.findByDelete(pageable);
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
        Optional<Member> member = memberRepository.findById(id);
        Optional<DeleteMember> deleteMember = deleteMemberRepository.findByMember(id);
            DeleteMemberDto dto = new DeleteMemberDto();
            dto.setPreLevel(member.get().getLevels());
            if(member.isPresent()){
                //탈퇴되면 탈퇴로그에 저장
                if(level.equals("6")){
                    log.info("====일치",level);
                    DeleteMember inserDeleteMember = DeleteMemberDto.toEntity(dto,member.get());
                    deleteMemberRepository.save(inserDeleteMember);
                    return member.get().updateLevel(level);
                }else {
                    if (deleteMember.isPresent()){
                        deleteMemberRepository.delete(deleteMember.get());
                        level = deleteMember.get().getPreLevel();
                        return member.get().updateLevel(level);
                    }else{
                        return member.get().updateLevel(level);
                    }
                }
            }

        throw new RuntimeException("없음");
    }

    @Transactional
    public Member updateLevelAll(String level, List<Long> id) {
        DeleteMemberDto dto = new DeleteMemberDto();
        List<Member> memberList = memberRepository.findLevelAll(id);
        List<DeleteMember> deleteMemberList =  deleteMemberRepository.findByDeletelAll(id);
        Member member = null;
        for (int i=0;i<memberList.size();i++){
            dto.setPreLevel(memberList.get(i).getLevels());
            if(level.equals("6")){
                log.info("====일치",level);
                DeleteMember deleteMember = DeleteMemberDto.toEntity(dto,memberList.get(i));
                deleteMemberRepository.save(deleteMember);
                member=  memberList.get(i).updateLevel(level);
            }else if(deleteMemberList.size()>0) {
                deleteMemberRepository.delete(deleteMemberList.get(i));
                level = deleteMemberList.get(i).getPreLevel();
                member=  memberList.get(i).updateLevel(level);
            }else {
                member=  memberList.get(i).updateLevel(level);
            }
        }
        return member;
    }
}
