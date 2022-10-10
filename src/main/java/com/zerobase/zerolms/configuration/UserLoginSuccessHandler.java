package com.zerobase.zerolms.configuration;

import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.service.LogHistoryService;
import com.zerobase.zerolms.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final LogHistoryService service;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        // IP, 세션 ID
        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println("IP : " + web.getRemoteAddress());
        System.out.println("Session ID : " + web.getSessionId());

        // 인증 ID
        System.out.println("name : " + authentication.getName());


        HistoryDto dto = new HistoryDto();
        dto.setUserId(authentication.getName());
        dto.setUserAg(RequestUtil.getUserAgent(request));
        dto.setUserIp(RequestUtil.getUserIp(request));


         boolean result = service.insertThings(dto);

         if(result){
             log.info("result success");
         }

        String uri ="/";

        response.sendRedirect(uri);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {



        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }
}
/*   // 권한 리스트
        List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
        System.out.print("권한 : ");
        for(int i = 0; i< authList.size(); i++) {
            System.out.print(authList.get(i).getAuthority() + " ");
        }
        System.out.println();*/