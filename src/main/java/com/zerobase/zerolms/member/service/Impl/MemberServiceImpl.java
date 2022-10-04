package com.zerobase.zerolms.member.service.Impl;

import com.zerobase.zerolms.components.MailComponents;
import com.zerobase.zerolms.member.entity.Member;
import com.zerobase.zerolms.member.exception.MemberNotEmailAuthException;
import com.zerobase.zerolms.member.model.MemberInput;
import com.zerobase.zerolms.member.model.ResetPasswordInput;
import com.zerobase.zerolms.member.repository.MemberRepository;
import com.zerobase.zerolms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MailComponents mailComponents;
    private final MemberRepository memberRepository;
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());


        String uuid= UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid).build();


        memberRepository.save(member);

        /*과제? 관리자페이지 메일수정*/
        String email =parameter.getUserId();
        String subject = "fastlms 사이트 가입을 축하드립니다.";
        String text="<p> fastlms 사이트 가입을 축하드립니다. </p><p> 아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
                    +"<div><a href= 'http://localhost:8080/member/email-auth?id="+ uuid +"'>가입 완료</a> </div>";

        mailComponents.sendMail(email,subject,text);

        return true;
    }


    @Override
    public boolean emailAuth(String uuid) {



       Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();

        if(member.isEmailAuthYn()){
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }



    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter){

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(parameter.getUserId(),parameter.getUserName());

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email =parameter.getUserId();
        String subject = "[fastlms] 비밀번호 초기화 메일입니다.";
        String text="<p>[fastlms] 비밀번호 초기화 메일입니다. </p><p> 아래 링크를 클릭하셔서 비밀번호초기화를 완료하세요.</p>"
                +"<div><a target='_blank' href= 'http://localhost:8080/member/reset/password?id="+ uuid +"'>비밀번호초기화 링크</a></div>";

        mailComponents.sendMail(email,subject,text);
        return true;
    }


    @Override
    public boolean resetPassword(String uuid, String password) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member =optionalMember.get();
        //초기화 날짜가 유효한지 체크
        if(member.getResetPasswordLimitDt() ==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        String encPassword=BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);
        return true;

    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);

        if(!optionalMember.isPresent()){
          return false;
        }

        Member member =optionalMember.get();
        //초기화 날짜가 유효한지 체크
        if(member.getResetPasswordLimitDt() ==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member =optionalMember.get();

        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인해주세요. ");
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(),member.getPassword(),grantedAuthorityList);

    }

}
