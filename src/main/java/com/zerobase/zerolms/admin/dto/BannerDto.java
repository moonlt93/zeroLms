package com.zerobase.zerolms.admin.dto;


import com.zerobase.zerolms.admin.entity.Banner;
import com.zerobase.zerolms.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BannerDto {

    Long id;

    String bannerName;
    String bannerFile;
    String bannerLink;
    String bannerStatus;
    String bannerFileLink;
    String sortValues;
    boolean bannerYn;
    LocalDateTime regDt;
    long totalCount;


    public static List<BannerDto> of(List<Banner> banner){

        if(banner != null){
            List<BannerDto> bannerList = new ArrayList<>();
            for(Banner x: banner){
                bannerList.add(of(x));
            }
            return bannerList;
        }
        return null;
    }


    public static BannerDto of(Banner banner) {

        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .bannerFile(banner.getBannerFile())
                .bannerLink(banner.getBannerLink())
                .bannerStatus(banner.getBannerStatus())
                .bannerYn(banner.isBannerYn())
                .regDt(LocalDateTime.now())
                .sortValues(banner.getSortValues())
                .bannerFileLink(banner.getBannerFileLink())
                .build();
    }
}
