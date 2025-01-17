package com.kj.member.controller;

import com.kj.deleteMember.dto.DeleteMemberDto;
import com.kj.log.dto.LogDto;
import com.kj.member.dto.*;
import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import com.kj.memberList.MemberListService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberListService memberListService;

    private int paginationSize=5;
    @GetMapping("/login")
    public String login(Model model, @CookieValue(value = "cookieID", required = false)Cookie cookie,@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception
                        ){
        model.addAttribute("loginDto",new LoginDto());
         if (cookie!=null){
            String userId = cookie.getValue();
            model.addAttribute("userId",userId);
            log.info("==={}",userId);
        }
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
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
    public String join(Model model,@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "exception", required = false) String exception){
        model.addAttribute("joinDto",new JoinDto());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/member/insert";
    }

    @PostMapping("/insert")
    public String joinProcess(@Valid @ModelAttribute JoinDto joinDto,
                              BindingResult bindingResult , Model model, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            model.addAttribute("joinDto",joinDto);
            return "member/insert";
        }
        memberService.insertMember(joinDto);
        ModalDto modalDto = ModalDto.builder()
                .isState("success")
                .msg("회원이 되었습니다. 축하합니다.")
                .build();
        redirectAttributes.addFlashAttribute("modalDto",modalDto);
        return "redirect:/member/login";
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
    @Transactional
    @PostMapping("/update")
    public String updateProcess(@ModelAttribute MemberDto memberDto, Model model,
                                @AuthenticationPrincipal CustomUserDetails customUserDetails){
        log.info("=={}",memberDto.getId());
        Member memberInfo = memberService.updateMember(memberDto);
        //계정 이름 변환
        customUserDetails.getLoggedMember().update(memberDto);
        return "redirect:/";
    }

   /* @GetMapping("/delete")
    public String delete(@RequestParam Long id, @AuthenticationPrincipal CustomUserDetails customUserDetails){

        return "/member/delete";
    }*/

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> updateLevel(Model model, String level, String userId , String password, Long id, DeleteMemberDto dto){
        log.info("id==={}",id);
        log.info("id==={}",password);
        boolean result =  memberService.deleteMember(id,password);
        Map<String,Object> resultMap = new HashMap<>();
        if (result) {
            memberService.updateLevel(level, id,dto);
            resultMap.put("isLevel", true);
        }else{
            resultMap.put("isLevel", false);
        }
        return resultMap;
    }




    @ResponseBody
    @GetMapping("/nickNameCheck")
    public Map<String,Object> nickNameCheck(String nickName){
        Map<String,Object> resultMap = new HashMap<>();
        boolean nickNameCheck = memberService.findByNickName(nickName);
        log.info("==={}",nickNameCheck);
        resultMap.put("nickNameCheck",nickNameCheck);
        return resultMap;
    }
    @ResponseBody
    @GetMapping("/idCheck")
    public Map<String,Object> idCheck(String userId){
        log.info("유저아이디",userId);
        Map<String,Object> resultMap = new HashMap<>();
        Integer count = memberService.findByUserId(userId);
        resultMap.put("count",count);
        return resultMap;
    }
}
