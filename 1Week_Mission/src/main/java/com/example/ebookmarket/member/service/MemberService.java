package com.example.ebookmarket.member.service;

import com.example.ebookmarket.base.dto.EmailDto;
import com.example.ebookmarket.member.entity.AuthLevel;
import com.example.ebookmarket.member.entity.Member;
import com.example.ebookmarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder encoder;

    private final MemberRepository memberRepository;

    private final JavaMailSender mailSender;

    public Member join(Member member) {

        member.setAuthLevel(AuthLevel.USER);

        String rawPassword = member.getPassword();
        String encodePassword = encoder.encode(rawPassword);

        member.setPassword(encodePassword);

        memberRepository.save(member);

        EmailDto emailDto = createJoinEmail(member.getEmail());
        sendEmail(emailDto);

        return member;

    }

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public Member modify(Member member, String email, String nickname) {

        member.setEmail(email);
        member.setNickname(nickname);

        memberRepository.save(member);

        return member;

    }

    public boolean checkPassword(Member member, String password) {

        String encodePassword = encoder.encode(password);

        if (member.getPassword() == encodePassword) {
            return true;
        }
        else {
            return false;
        }

    }

    public Member modifyPassword(Member member, String oldPassword, String password, String passwordConfirm) {


        if (!checkPassword(member, oldPassword)) {
            return member;
        }

        if (password != passwordConfirm) {
            return member;
        }

        String encodePassword = encoder.encode(password);

        member.setPassword(encodePassword);

        memberRepository.save(member);

        return member;
    }

    public String findUsername(String email) {

        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member != null) {
            return member.getUsername();
        } else {
            return null;
        }

    }

    public String findPassword(String username, String email) {

        Member member = memberRepository.findByUsername(username).orElse(null);

        if (!member.getEmail().equals(email) || member == null) {

            return null;
        }

        String tempPassword = String.valueOf(UUID.randomUUID()).substring(0, 15);

        changeTempPassword(member, tempPassword);
        EmailDto emailDto = createTempPasswordEmail(email, tempPassword);
        sendEmail(emailDto);

        return tempPassword;
    }

    private void sendEmail(EmailDto emailDto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getAddress());
        message.setSubject(emailDto.getTitle());
        message.setText(emailDto.getMessage());
        message.setFrom("chwil2989@naver.com");
        message.setReplyTo("chwil2989@naver.com");
        System.out.println("message"+message);
        mailSender.send(message);

    }

    private EmailDto createTempPasswordEmail(String email, String tempPassword) {

        EmailDto emailDto = EmailDto.builder()
                .address(email)
                .title("EBOOK ?????????????????? ?????? ??????????????????.")
                .message("???????????????. EBOOK ?????? ???????????? ?????? ?????? ??????????????????. ???????????? ?????? ??????????????? " + tempPassword + " ?????????. ????????? ?????? ??????????????? ??????????????????.")
                .build();

        return emailDto;
    }

    private EmailDto createJoinEmail(String email) {

        EmailDto emailDto = EmailDto.builder()
                .address(email)
                .title("EBOOK ?????? ?????? ??????????????????.")
                .message("???????????????. EBOOK ????????? ??????????????????.")
                .build();

        return emailDto;
    }

    private void changeTempPassword(Member member, String tempPassword) {

        String encodePassword = encoder.encode(tempPassword);

        member.setPassword(encodePassword);

        memberRepository.save(member);

    }

    public boolean checkUsernameOverlap(String username) {

        Optional<Member> result = memberRepository.findByUsername(username);

        if (result.isPresent()) {
            return true;
        }

        return false;
    }
}
