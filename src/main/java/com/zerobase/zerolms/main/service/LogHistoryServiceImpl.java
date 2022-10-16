package com.zerobase.zerolms.main.service;

import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.entity.LoginHistory;
import com.zerobase.zerolms.main.mapper.HistoryMapper;
import com.zerobase.zerolms.main.repository.LogHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class LogHistoryServiceImpl implements LogHistoryService {

    private final LogHistoryRepository logHistoryRepository;
    private final HistoryMapper mapper;

    @Override
    public boolean insertThings(HistoryDto param) {

        LoginHistory log = LoginHistory.builder()
                .UserId(param.getUserId())
                .UserIp(param.getUserIp())
                .UserAg(param.getUserAg())
                .ConDate(LocalDateTime.now())
                .build();
        logHistoryRepository.save(log);
        return true;

    }

    @Override
    public List<HistoryDto> frontList(String userId) {
        return mapper.selectFullList(userId);
    }
}
