package com.zerobase.zerolms.main.service;


import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.param.historyParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface LogHistoryService {
    boolean insertThings(HistoryDto dto);

    List<HistoryDto> frontList(String userId);
}
