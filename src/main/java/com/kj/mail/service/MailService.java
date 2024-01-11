package com.kj.mail.service;

import com.kj.mail.dto.MailDto;
import com.kj.mail.repository.MailRepository;
import com.kj.member.entity.Member;
import com.kj.utils.RandomNumPassword;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final MailRepository mailRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RandomNumPassword randomNumPassword;

    public Member findId(MailDto mailDto){
        Optional<Member> member = mailRepository.findByEmail(mailDto.getEmail()); //이메일 ID 확인
        if(member.isPresent()){
            if(!member.get().getUserName().equals(mailDto.getUserName())){ //입력한 이름과 DB의 이름이 일치한지 확인
                return null;
            }
            sendIDMail(mailDto,member.get().getUserId()); //메일을 userID를 포함하여 보내는 메서드
            return member.get(); //반환한다 엔티티로
        } else {
            return null;
        }
    }
    @Transactional
    public Member findPw(MailDto mailDto,String userId){
        Optional<Member> member = mailRepository.findByEmail(mailDto.getEmail());
        if(member.isPresent()){
            if(member.get().getUserName().equals(mailDto.getUserName()) && member.get().getUserId().equals(userId)){
                String newPassword = randomNumPassword.createPassWord();
                member.get().updatePassword(newPassword);
                sendPasswordMail(mailDto,newPassword);
                return member.get();
            }else {
                return null;
            }

        }else {
            return null;
        }
    }

    public MimeMessage sendIDMail(MailDto mailDto,String userId){ //메일 관련 메서드
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

    public MimeMessage sendPasswordMail(MailDto mailDto,String newPassword){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("kimhg1103@naver.com");  // 보내는 사람
            message.setRecipients(MimeMessage.RecipientType.TO,mailDto.getEmail());  // 받는 사람
            message.setSubject("요청하신 임시 비밀번호입니다.");
            message.setText("안녕하세요"+mailDto.getUserName()+newPassword,"UTF-8","html");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public MimeMessage sendNumMail(String mail){ //메일 관련 메서드
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom("kimhg1103@naver.com");  // 보내는 사람
            message.setRecipients(MimeMessage.RecipientType.TO,mail);  // 받는 사람
            message.setSubject("요청하신 인증번호입니다.");
            message.setText("안녕하세요"+randomNumPassword.createRandomNum(),"UTF-8","html");
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public Integer sendAuthEmail(String mail) {
        MimeMessage message = sendNumMail(mail);
        //db 비밀번호를 생성된 비밀번호르르안호화 해서 넣어둔다.
        javaMailSender.send(message);
        return randomNumPassword.createRandomNum();
    }

}
