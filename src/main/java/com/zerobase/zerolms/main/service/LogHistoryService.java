package com.zerobase.zerolms.main.service;


import com.zerobase.zerolms.main.dto.HistoryDto;

import java.util.List;

public interface LogHistoryService {
    boolean insertThings(HistoryDto dto);

    List<HistoryDto> frontList(String userId);
}
