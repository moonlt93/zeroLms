package com.zerobase.zerolms.admin.mapper;


import com.zerobase.zerolms.admin.dto.BannerDto;
import com.zerobase.zerolms.admin.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {


    List<BannerDto> selectList(BannerParam param) ;

    long selectListCount(BannerParam param);

    List<BannerDto> selectImagePath();

}
