package com.zerobase.zerolms.member.service;

import com.zerobase.zerolms.member.model.MemberInput;
import com.zerobase.zerolms.member.model.ResetPasswordInput;
import com.zerobase.zerolms.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface MemberService extends UserDetailsService {



    boolean register(MemberInput parameter);

    /*
    * uuid계정 활성화
    * */
    boolean emailAuth(String uuid);

    /*
    * 입력한 이메일로 비밀번호 초기화 정보를 전송
    * */
    boolean sendResetPassword(ResetPasswordInput input);

    /*
    * 입력받은 uuid에 대해서 password초기화 진행
    * */
    boolean resetPassword(String id, String password);

    /*
    * 입력 받은 uuid 값이 유효할지 확인
    * @param uuid
    * */
    boolean checkResetPassword(String uuid);
}
