package com.zerobase.zerolms.admin.service;

import com.zerobase.zerolms.admin.dto.BannerDto;
import com.zerobase.zerolms.admin.entity.Banner;
import com.zerobase.zerolms.admin.mapper.BannerMapper;
import com.zerobase.zerolms.admin.model.BannerParam;
import com.zerobase.zerolms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService{

    private final BannerMapper bannerMapper;
    private final BannerRepository bannerRepository;
    @Override
    public List<BannerDto> frontList(BannerParam param) {

        long totalCount = bannerMapper.selectListCount(param);
        param.setTotalCount(totalCount);

        return bannerMapper.selectList(param);
    }

    @Override
    public boolean addPic(BannerParam param) {

        Banner banner = Banner.builder()
                .bannerFile(param.getBannerFile())
                .bannerLink(param.getBannerLink())
                .bannerName(param.getBannerName())
                .bannerStatus(param.getBannerStatus())
                .bannerYn(param.isBannerYn())
                .sortValues(param.getSortValues())
                .regDt(LocalDateTime.now())
                .bannerFileLink(param.getBannerFileLink())
                .build();
        bannerRepository.save(banner);
        return true;

    }

    @Override
    public boolean set(BannerParam param) {

        Optional<Banner> optionalBanner = bannerRepository.findById(param.getId());

        if(!optionalBanner.isPresent()){
            return false;
        }
        String split= param.getRegDt();
        String first = split.substring(0,split.indexOf('T'));
        String second = split.substring(split.indexOf('T')+1);

        String result= first+" "+second;

        DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime fromDate= LocalDateTime.parse(result,dateformat);


        Banner banner = optionalBanner.get();
        banner.setBannerFile(param.getBannerFile());
        banner.setBannerName(param.getBannerName());
        banner.setBannerLink(param.getBannerLink());
        banner.setBannerYn(param.isBannerYn());
        banner.setBannerStatus(param.getBannerStatus());
        banner.setRegDt(fromDate);
        banner.setSortValues(param.getSortValues());
        banner.setBannerFileLink(param.getBannerFileLink());
        bannerRepository.save(banner);

        return true;
    }

    @Override
    public BannerDto getById(long id) {
        Optional<Banner> OptionalBanner
                = bannerRepository.findById(id);

        if(OptionalBanner.isPresent()){
            return BannerDto.of(OptionalBanner.get());
        }
        return null;
    }

    @Override
    public boolean del(String idList) {
        if(idList != null && idList.length()>0){
            String [] ids = idList.split(",");
            for (String x: ids
                 ) {
                long id = 0L;
                try{
                    id= Long.parseLong(x);
                }catch (Exception e){

                }
                if(id>0){
                    bannerRepository.deleteById(id);
                }

            }
        }

        return true;

    }

    @Override
    public boolean updateStatus(String bannerStatus, long id) {
        Optional<Banner> optionalBanner = bannerRepository.findById(id);

        if(!optionalBanner.isPresent()){
            throw new UsernameNotFoundException("배너 정보가 존재하지 않습니다.");
        }
        Banner banner = optionalBanner.get();

        banner.setBannerStatus(bannerStatus);
        bannerRepository.save(banner);

        return true;
    }


    @Override
    public List<BannerDto> getFileRoot() {

        return bannerMapper.selectImagePath();
    }
}
