package com.zerobase.zerolms.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMemberController {

    @GetMapping("/admin/member/list")
        public String list(){

        return "admin/member/list";
        }

}
