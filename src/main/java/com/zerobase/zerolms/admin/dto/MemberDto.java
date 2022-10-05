package com.zerobase.zerolms.admin.dto;

import com.zerobase.zerolms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberDto {

    String userId;

    String userName;
    String phone;
    String password;
    LocalDateTime regDt;


    boolean emailAuthYn;
    String emailAuthKey;
    LocalDateTime emailAuthDt;
    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;


    //관리자여부 지정
    boolean adminYn;

    //페이징
    Long totalCount;
    long seq;

    String userStatus;

    public static MemberDto of(Member member) {

        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .phone(member.getPhone())
                //.password(member.getPassword())
                .regDt(member.getRegDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .build();
    }
}