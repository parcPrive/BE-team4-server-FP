package com.kj.admin;


import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.MemberDto;
import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AdminController {
    private final MemberService memberService;

    @GetMapping("/admin")
    @ResponseBody
    public String test(){
        return "admin";
    }
    @GetMapping("/admin/mypage/{id}")
    public String mypage(@PathVariable Long id, Model model){
        log.info("=={}",id);

        MemberDto memberInfo = memberService.findByAdminMemberId(id);
        log.info("=={}",memberInfo.getUserId());
        log.info("=={}",memberInfo.getProfileImageUrl());
        model.addAttribute("memberInfo",memberInfo);
        return "/member/mypage";
    }
    @GetMapping("/admin/update/{id}")
    public String update(@PathVariable Long id, Model model){
        log.info("=={}","ㅇㅇㅇ");
        MemberDto memberInfo = memberService.findByAdminMemberId(id);
        model.addAttribute("memberInfo",memberInfo);
        return "/member/update";
    }
    @PostMapping("/admin/update")
    public String updateProcess(@ModelAttribute MemberDto memberDto, Model model,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Member member = memberService.updateMember(memberDto);
        MemberDto memberInfo = MemberDto.toDto(member);
        model.addAttribute("memberInfo",memberInfo);
        return "/member/mypage";
    }
}
