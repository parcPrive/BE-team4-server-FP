package com.kj.member.controller;

import com.kj.log.dto.LogDto;
import com.kj.member.dto.*;
import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private int paginationSize=5;
    @GetMapping("/login")
    public String login(Model model, @CookieValue(value = "cookieID", required = false)Cookie cookie){
        model.addAttribute("loginDto",new LoginDto());
         if (cookie!=null){
            String userId = cookie.getValue();
            model.addAttribute("userId",userId);
            log.info("==={}",userId);
        }
        return "/member/login";
    }
    @PostMapping("/login")
    public String login(LoginDto loginDto, LogDto logDto, HttpServletResponse response, Model model) {
        String userId = loginDto.getUserId();
        String password = loginDto.getPassword();
        String check = loginDto.getCheck();
        log.info("check= {}", check);
        log.info("request userid = {}, password = {}", userId, password);
        memberService.login(userId, password,response,check,logDto);
        //log.info("jwtToken accessToken = {}, refreshToken = {}", tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        return "redirect:/";
    }

    @GetMapping("/insert")
    public String join(Model model){
        model.addAttribute("joinDto",new JoinDto());
        return "/member/insert";
    }

    @PostMapping("/insert")
    public String joinProcess(@Valid @ModelAttribute JoinDto joinDto,
                              BindingResult bindingResult , Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("joinDto",joinDto);
            return "member/insert";
        }
        memberService.insertMember(joinDto);
        return "/member/login";
    }

    @GetMapping("/mypage/{id}")
    public String mypage(@PathVariable Long id, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("=={}",id);

        MemberDto memberInfo = memberService.findByMemberId(id,customUserDetails);
        log.info("=={}",memberInfo.getUserId());
        log.info("=={}",memberInfo.getProfileImageUrl());
        model.addAttribute("memberInfo",memberInfo);
        return "/member/mypage";
    }

    @GetMapping("/update")
    public String update(@RequestParam Long id,Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        MemberDto memberInfo = memberService.findByMemberId(id,customUserDetails);
        model.addAttribute("memberInfo",memberInfo);
        return "/member/update";
    }
    @PostMapping("/update")
    public String updateProcess(@ModelAttribute MemberDto memberDto, Model model,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){

        Member member = memberService.updateMember(memberDto);
        MemberDto memberInfo = MemberDto.toDto(member);
        //계정 이름 변환
        customUserDetails.getLoggedMember().update(memberDto);
        model.addAttribute("memberInfo",memberInfo);
        return "/member/mypage";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        return "/member/delete";
    }

    @PostMapping("/delete")
    public String deleteProcess(@RequestParam String password, Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails){

       boolean result = memberService.deleteMember(customUserDetails.getLoggedMember().getId(), password);
       log.info("=={}",customUserDetails.getLoggedMember().getId());
       log.info("=={}",customUserDetails.getPassword());
        log.info("=={}",password);

       if (result) {
            return "redirect:/member/logout";
        } else {
            //model.addAttribute("wrongPassword", "비밀번호가 맞지 않습니다.");
            return "/member/join";
        }
    }





    @ResponseBody
    @GetMapping("/nickNameCheck")
    public Map<String,Boolean> nickNameCheck(String nickName){
        Map<String,Boolean> resultMap = new HashMap<>();
        boolean nickNameCheck = memberService.findByNickName(nickName);
        log.info("==={}",nickNameCheck);
        resultMap.put("nickNameCheck",nickNameCheck);
        return resultMap;
    }
}
