package com.kj.mail;

import com.kj.mail.dto.MailDto;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Member findId(MailDto mailDto){
        Optional<Member> member = mailRepository.findByEmail(mailDto.getEmail()); //이메일 ID 확인
        if(member.isPresent()){
            if(!member.get().getUserName().equals(mailDto.getUserName())){ //입력한 이름과 DB의 이름이 일치한지 확인
                return null;
            }
            sendMail(mailDto,member.get().getUserId()); //메일을 userID를 포함하여 보내는 메서드
            return member.get(); //반환한다 엔티티로
        } else {
            return null;
        }
    }
    public Member findPw(MailDto mailDto,String userId){
        Optional<Member> member = mailRepository.findByEmail(mailDto.getEmail());
        if(member.isPresent()){
            if(member.get().getUserName().equals(mailDto.getUserName()) && member.get().getUserId().equals(userId)){
                sendPasswordMail(mailDto);
                return member.get();
            }else {
                return null;
            }

        }else {
            return null;
        }
    }

    public MimeMessage sendMail(MailDto mailDto,String userId){ //메일 관련 메서드
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("kimhg1103@naver.com");  // 보내는 사람
            message.setRecipients(MimeMessage.RecipientType.TO,mailDto.getEmail());  // 받는 사람
            message.setSubject("요청하신아이디입니다.");
            message.setText("안녕하세요"+mailDto.getUserName()+userId,"UTF-8","html");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public MimeMessage sendPasswordMail(MailDto mailDto){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("kimhg1103@naver.com");  // 보내는 사람
            message.setRecipients(MimeMessage.RecipientType.TO,mailDto.getEmail());  // 받는 사람
            message.setSubject("요청하신 임시 비밀번호입니다.");
            message.setText("안녕하세요"+mailDto.getUserName(),"UTF-8","html");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }


}
