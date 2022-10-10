package com.zerobase.zerolms.main.dto;

import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.entity.Category;
import com.zerobase.zerolms.main.entity.LoginHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HistoryDto {



    String userId;
    LocalDateTime conDate;
    String userIp;
    String userAg;
    int NO;




    public static HistoryDto of(LoginHistory history){

        return HistoryDto.builder()
                .userId(history.getUserId())
                .conDate(history.getConDate())
                .userIp(history.getUserIp())
                .userAg(history.getUserAg())
                .build();
    }


    public static List<HistoryDto> of(List<LoginHistory> list) {
             if(list != null){
            List<HistoryDto> historyList = new ArrayList<>();
            for(LoginHistory x: list){
                historyList.add(of(x));
            }
            return historyList;
        }
        return null;

    }


}
