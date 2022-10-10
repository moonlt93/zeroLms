package com.zerobase.zerolms.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestUtil {
    public static String getUserAgent(HttpServletRequest req) {

     String browser="";

     String userAg = req.getHeader("user-agent");
        if(userAg.indexOf("Trident") > -1) {												// IE
            browser = "ie";
        } else if(userAg.indexOf("Edge") > -1) {											// Edge
            browser = "edge";
        } else if(userAg.indexOf("Whale") > -1) { 										// Naver Whale
            browser = "whale";
        } else if(userAg.indexOf("Opera") > -1 || userAg.indexOf("OPR") > -1) { 		// Opera
            browser = "opera";
        } else if(userAg.indexOf("Firefox") > -1) { 										 // Firefox
            browser = "firefox";
        } else if(userAg.indexOf("Safari") > -1 && userAg.indexOf("Chrome") == -1 ) {	 // Safari
            browser = "safari";
        } else if(userAg.indexOf("Chrome") > -1) {										 // Chrome
            browser = "Chrome";
        }

        String userAgent= userAg.substring(userAg.indexOf("Chrome"),userAg.indexOf("Safari")-1);

        log.info("user-agent:"+userAgent);
        log.info("Browser: "+ browser);

        StringBuilder sb = new StringBuilder();
        sb.append(userAgent).append(",").append(browser);

     return sb.toString();
    }

    public static String getUserIp(HttpServletRequest req) {

        String ip = req.getHeader("X-Forwarded-For");
        if(ip==null){
            ip = req.getRemoteAddr();
        }

        return ip;
    }
}
