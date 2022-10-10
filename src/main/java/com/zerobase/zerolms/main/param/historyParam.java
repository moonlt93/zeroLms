package com.zerobase.zerolms.main.param;

import com.zerobase.zerolms.admin.model.CommonParam;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class historyParam extends CommonParam {

    String UserId;
    LocalDateTime ConDate;
    String UserIp;
    String UserAg;
}
