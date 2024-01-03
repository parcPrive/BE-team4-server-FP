//package com.kj.mail;
//
//import com.kj.mail.dto.MailDto;
//import com.kj.member.dto.MemberDto;
//import com.kj.member.entity.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/mail")
//public class MailController {
//    private final MailService mailService;
//    @GetMapping("/findId")
//    public String findId(){
//        return "/mail/findId";
//    }
//    @PostMapping("/findId")
//    public String findId(@ModelAttribute MailDto mailDto, Model model){
//        Member member = mailService.findId(mailDto);
//        MemberDto memberDto = MemberDto.toDto(member);
//        model.addAttribute("memberDto",memberDto);
//        return "/member/login";
//    }
//    @GetMapping("/findPw")
//    public String findPw(){
//        return "/mail/findPw";
//    }
//    @PostMapping("/findPw")
//    public String findPw(@ModelAttribute MailDto mailDto, @RequestParam String userId, Model model){
//        mailService.findPw(mailDto,userId);
//        return "/member/insert";
//    }
//}
