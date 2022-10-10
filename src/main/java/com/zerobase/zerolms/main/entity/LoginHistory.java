package com.zerobase.zerolms.main.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistory {

    @Id
    String UserId;
    LocalDateTime ConDate;
    String UserIp;
    String UserAg;

}
