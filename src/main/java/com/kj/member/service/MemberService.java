package com.kj.member.service;

import com.kj.jwt.JwtUtil;
import com.kj.log.dto.LogDto;
import com.kj.log.entity.Log;
import com.kj.log.repository.LogRepository;
import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.JoinDto;
import com.kj.member.dto.MemberDto;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final LogRepository logRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtil jwtUtil;

    @Value("${file.path}")
    private String uploadFolder;

    @Transactional
    public void login(String userId, String password, HttpServletResponse response, String check , LogDto logDto){
        //아이디 체크
        Optional<Member> optionalMember = memberRepository.findByUserId(userId);
        if (optionalMember.isEmpty()){
            log.warn("회원이 존재하지 않음");
            throw new IllegalArgumentException("회원이 존재하지 않음");
        }
        Member member = optionalMember.get();
        Log logMember = LogDto.toEntity(logDto,member);
        logRepository.save(logMember);

        // PW 체크하고
        if (!bCryptPasswordEncoder.matches(password,member.getPassword())){
            log.warn("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        /*토큰을 쿠키로 발급 및 응답에 추가*/
        //성공시 토큰 생성
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(member.getUserId(), member.getRole()));
        cookie.setMaxAge(60*60); // 1시간 동안 유효
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setSecure(false);

        if(check!=null){
            Cookie cookieID = new Cookie("cookieID",userId);
            cookieID.setMaxAge(30* 60); // 30분만 유효
            cookieID.setPath("/");
            cookieID.setDomain("localhost");
            cookieID.setSecure(false);
            response.addCookie(cookieID);
        }else {
            Cookie cookieID = new Cookie("cookieID",userId);
            cookieID.setMaxAge(0); // 1일 동안 유효
            cookieID.setPath("/");
            response.addCookie(cookieID);
        }

        response.addCookie(cookie);

    }

    public JoinDto insertMember(JoinDto joinDto){
        Member member = JoinDto.toEntity(joinDto);
        memberRepository.save(member);
        return joinDto;
    }


    public MemberDto findByMemberId(Long id, CustomUserDetails customUserDetails) {
        if(id==customUserDetails.getLoggedMember().getId()){
            Member findId = memberRepository.findById(id).orElse(null);
            MemberDto memberDto = MemberDto.toDto(findId);
            return memberDto;
        }else {
            Member findId = memberRepository.findById(id).orElse(null);
            MemberDto memberDto = MemberDto.toDto(findId);
            return memberDto;
        }
            /*throw new RuntimeException("없는 사람입니다");*/
    }

    public MemberDto findByAdminMemberId(Long id) {
            Member findId = memberRepository.findById(id).orElse(null);
            MemberDto memberDto = MemberDto.toDto(findId);
            return memberDto;
    }


    @Transactional
    public Member updateMember(MemberDto memberDto) {
        Optional<Member> member = memberRepository.findById(memberDto.getId());
        if(member.isPresent()){
          return member.get().update(memberDto);
        }
        throw new RuntimeException("없음");
    }


    @Transactional
    public boolean deleteMember(Long id,String password) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
        new UsernameNotFoundException("아이디 존재하지 않습니다."));
        if (bCryptPasswordEncoder.matches(password, member.getPassword())){
            memberRepository.delete(member);
            return true;
        }else {
            return false;
        }

    }

    @Transactional
    public Member changeProfile(Long id, MultipartFile profileImageUrl) {
        log.info("id===={}",id);
        String originalFileName = profileImageUrl.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid+"_"+profileImageUrl.getOriginalFilename();
        String thumbnailFileName = "thumb_"+imageFileName;

        Path imageFilePath = Paths.get(uploadFolder+imageFileName);
        File originalFile = new File(uploadFolder+imageFileName);
        try {
            Files.write(imageFilePath,profileImageUrl.getBytes());
            /*Thumbnailator.createThumbnail(originalFile,
                    new File(uploadFolder+thumbnailFileName),150,150);
            originalFile.delete();*/

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Optional<Member> optionalMember = memberRepository.findById(id);  // member
        if(optionalMember.isPresent()) {

            return optionalMember.get().updateProfile(imageFileName);
        } else {
            throw new UsernameNotFoundException("서버 오류입니다.");
        }
    }

    public List<MemberDto> findListMember() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberDto> memberDtoList = new ArrayList<>();
        for (int i=0;i<memberList.size();i++){
            memberDtoList.add(MemberDto.toDto(memberList.get(i)));
        }
        return memberDtoList;
    }





    public boolean findByNickName(String nickName) {
        Optional<Member> findMember = memberRepository.findByNickName(nickName);
        if (findMember.isPresent()){
            return true;
        }else {
            return false;
        }
    }

    // 현목 주문에 필요한 회원 기본 배송지 데이터 필요 findByuserId or findByuserNickName
    public Member findByUserNickName(String userNickName){
        Optional<Member> findMember = memberRepository.findByNickName(userNickName);
        if(findMember.isPresent()){
            return findMember.get();
        }else{
            new RuntimeException("없는회원");
        }
        return null;
    }
}
