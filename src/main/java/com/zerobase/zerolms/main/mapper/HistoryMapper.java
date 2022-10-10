package com.zerobase.zerolms.main.mapper;

import com.zerobase.zerolms.admin.dto.MemberDto;
import com.zerobase.zerolms.admin.model.MemberParam;
import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.entity.LoginHistory;
import com.zerobase.zerolms.main.param.historyParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryMapper {

     List<HistoryDto> selectFullList(String userId);



}
