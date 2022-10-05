package com.zerobase.zerolms.admin.mapper;

import com.zerobase.zerolms.admin.dto.MemberDto;
import com.zerobase.zerolms.admin.model.MemberParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

     List<MemberDto> selectList(MemberParam param);
     long selectListCount(MemberParam param);

}
