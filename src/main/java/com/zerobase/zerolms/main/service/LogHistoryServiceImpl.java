package com.zerobase.zerolms.main.service;

import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.entity.LoginHistory;
import com.zerobase.zerolms.main.mapper.HistoryMapper;
import com.zerobase.zerolms.main.param.historyParam;
import com.zerobase.zerolms.main.repository.LogHistoryRepository;
import com.zerobase.zerolms.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


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
