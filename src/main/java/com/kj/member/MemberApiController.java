package com.kj.member;

import com.kj.member.entity.Member;
import com.kj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class MemberApiController {
    private final MemberService memberService;



    @PutMapping("/member/{id}/profileImageUrl")
    public Map<String,Object> profileImageUpdate(@PathVariable Long id,
                                                 MultipartFile profileImageUrl) {
        log.info("profileImageUrl==={}",profileImageUrl);
        Member memberInfo = memberService.changeProfile(id, profileImageUrl); //void
        log.info(memberInfo.toString());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("isState","ok");
        resultMap.put("memberInfo",memberInfo);

        return resultMap;
    }
}
