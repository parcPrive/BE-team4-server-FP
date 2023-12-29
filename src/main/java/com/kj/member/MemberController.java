package com.kj.member;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.MemberDto;
import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/member/join")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("memberDto",new MemberDto());
        return "/member/login";
    }


    @GetMapping("/join")
    public String join(){
        return "/member/join";
    }

    @PostMapping("/join")
    public String joinProcess(MemberDto memberDto){
        memberService.join(memberDto);
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String mypage(@RequestParam Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        MemberDto memberInfo = memberService.findMemberInfo(id,customUserDetails);
        model.addAttribute("memberInfo",memberInfo);
        return "/member/mypage";
    }

    @GetMapping("/modify")
    public String modify(@RequestParam Long id,Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        MemberDto memberInfo = memberService.findMemberInfo(id,customUserDetails);
        model.addAttribute("memberInfo",memberInfo);
        return "/member/modify";
    }
    @PostMapping("/modify")
    public String modifyProcess(@ModelAttribute MemberDto memberDto, Model modelm,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){

        memberService.updateMember(memberDto);
        return "/member/mypage";
    }
}
