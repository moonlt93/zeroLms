package com.zerobase.zerolms.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryDto {



    String UserId;
    LocalDateTime ConDate;
    String UserIp;
    String UserAg;

}
