package com.zerobase.zerolms.admin.service;

import com.zerobase.zerolms.admin.dto.BannerDto;
import com.zerobase.zerolms.admin.model.BannerParam;

import java.util.List;

public interface BannerService {
    List<BannerDto> frontList(BannerParam param);

    boolean addPic(BannerParam param);

    boolean set(BannerParam param);

    BannerDto getById(long id);

    boolean del(String idList);

    boolean updateStatus(String bannerStatus, long id);

    List<BannerDto> getFileRoot();
}
