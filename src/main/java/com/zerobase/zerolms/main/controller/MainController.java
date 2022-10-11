package com.zerobase.zerolms.main.controller;

//매핑을 위한 클래스
//주소(논리적인 주소) + 물리적인 파일(프로그래밍을 해야하니깐 => 매핑)

//어디서 매핑? 누가 매핑
// 하나의 주소에 대해 누가 해줄거냐
//https:// www.naver.com//new/list.do
// 클래스 , 속성, 메소드 ?

import com.zerobase.zerolms.admin.dto.BannerDto;
import com.zerobase.zerolms.admin.model.BannerParam;
import com.zerobase.zerolms.admin.service.BannerService;
import com.zerobase.zerolms.components.MailComponents;
import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.service.LogHistoryService;
import com.zerobase.zerolms.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


//request, => web => server
// response  server => web
//spring -> MVC(VIEW단 -> 템플릿엔진 화면에 내용을 출력)
//PYTHON DJANGO ->MTV(TEMPLATE =>화면 출력 )


  /*  @RequestMapping(value = "/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // printwriter
        //html파일이 아니므로 소스보기를 했을때만 이동했음    String str ="hello \r\n fastLms webPage!!";
        //한글이 깨져서 charset을 설정했지만 어차피 깨짐
        //response.setContentType으로 설정
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        String msg = " <head>\n" +
                "      <head>" +
                "      <meta charset=\"UTF-8\">" +
                "       </head>" +
                "       <body>\n" +
                "        <h1>hello</h1>\n" +
                "        <p>fastlms webSite!!!</p>\n" +
                "         <p> 안녕하세요!!!!</p>  " +
                "</body>\n" +
                "</html>";
        writer.write(msg);
        writer.close();

    }*/

   /*    String email ="moonlt932@gmail.com";
        String subject ="안녕하세요. 제로베이스 문진수 입니다.";
        String text=
                "<p>안녕하세요</p>"+
                "<p>반갑습니다.</p>"
                ;

        mailComponents.sendMail(email,subject,text);

*/