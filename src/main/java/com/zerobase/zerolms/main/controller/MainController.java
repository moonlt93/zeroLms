package com.zerobase.zerolms.main.controller;

//매핑을 위한 클래스
//주소(논리적인 주소) + 물리적인 파일(프로그래밍을 해야하니깐 => 매핑)

//어디서 매핑? 누가 매핑
// 하나의 주소에 대해 누가 해줄거냐
//https:// www.naver.com//new/list.do
// 클래스 , 속성, 메소드 ?

import com.zerobase.zerolms.admin.dto.BannerDto;
import com.zerobase.zerolms.admin.service.BannerService;
import com.zerobase.zerolms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {


    private final MailComponents mailComponents;
    private final BannerService bannerService;

    @RequestMapping(value = "/")
    public String index(Model model) {


        List<BannerDto> detail =  bannerService.getFileRoot();
        model.addAttribute("home__slider",detail);
        return "index";


    }

    @RequestMapping("/error/denied")
    public String errorDenied() {
        return "error/denied";
    }


}

