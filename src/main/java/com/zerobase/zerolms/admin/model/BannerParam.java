package com.zerobase.zerolms.admin.model;


import lombok.Data;

@Data
public class BannerParam {


   long id;

    String bannerName;
    String bannerFile;
    String bannerLink;
    String bannerStatus;
    String sortValues;
    boolean bannerYn;
    String regDt;
    String bannerFileLink;
    String idList;
    long totalCount;

}
