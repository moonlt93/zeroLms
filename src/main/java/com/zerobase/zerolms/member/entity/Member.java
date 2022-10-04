package com.zerobase.zerolms.member.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Member {

    @Id
    private String userId;

    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;



    private boolean emailAuthYn;
    private String emailAuthKey;
    private LocalDateTime emailAuthDt;
    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;


    //관리자여부 지정
    private boolean adminYn;

}
