package com.kj.member.service;

import com.kj.member.dto.CustomUserDetails;
import com.kj.member.dto.MemberDto;
import com.kj.member.dto.UpdateMemberDto;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${file.path}")
    private String uploadFolder;

    public MemberDto insertMember(MemberDto memberDto){
        Member member = MemberDto.toEntity(memberDto);
        memberRepository.save(member);
        return memberDto;
    }


    public MemberDto findByMemberId(Long id, CustomUserDetails customUserDetails) {
        if(id==customUserDetails.getLoggedMember().getId()){
            Member findId = memberRepository.findById(id).orElse(null);
            MemberDto memberDto = MemberDto.toDto(findId);
            return memberDto;
        }
            throw new RuntimeException("없는 사람입니다");
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
        log.info("=={}",member.getPassword());
        log.info("=={}",password);
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
}
