package com.zerobase.zerolms.member.model;

import lombok.*;

@Getter
@Setter
@ToString
public class MemberInput {

    private String userId;
    private String userName;
    private String password;
    private String phone;
    private String newPassword;

    private String zipcode;
    private String addr;
    private String addrDetail;

}
