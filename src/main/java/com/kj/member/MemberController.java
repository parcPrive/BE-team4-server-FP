package com.kj.member;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.MemberDto;
import com.kj.member.dto.MemberProfileDto;
import com.kj.member.dto.UpdateMemberDto;
import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private int paginationSize=5;
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("memberDto",new MemberDto());
        return "/member/login";
    }


    @GetMapping("/insert")
    public String join(){
        return "/member/insert";
    }

    @PostMapping("/insert")
    public String joinProcess(MemberDto memberDto){
        memberService.insertMember(memberDto);
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

    /*@GetMapping("/list")
    public String MemberList(Model model){
        List<MemberDto> memberList = memberService.findListMember();
        model.addAttribute("memberList",memberList);
        return "memberList/list";
    }*/

    @GetMapping("/list")
    public String list02(Model model, @RequestParam(value = "page", required = true, defaultValue = "0") int page) {
        Page<Member> pagination = memberService.getAllPageBoard(page);
        log.info("pageBoardList.getTotalPages()==={}",pagination.getTotalPages());
        log.info(pagination.toString());
        List<Member> memberList = pagination.getContent();
        int start = (int)(Math.floor((double) pagination.getNumber() / paginationSize)*paginationSize);
        int end =  start + paginationSize;

        log.info("start==={},end==={}",start,end);
        model.addAttribute("start",start);
        model.addAttribute("end",end);
        model.addAttribute("pagination",pagination);
        model.addAttribute("memberList",memberList);
        return "memberList/list";
    }
}
