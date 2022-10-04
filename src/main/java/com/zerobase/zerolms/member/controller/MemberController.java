package com.zerobase.zerolms.member.controller;

import com.zerobase.zerolms.member.model.MemberInput;
import com.zerobase.zerolms.member.model.ResetPasswordInput;
import com.zerobase.zerolms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequiredArgsConstructor
public class MemberController {

   private final MemberService memberService;


    @RequestMapping("/member/login")
    public String login(){

        System.out.println("get");

        return "member/login";

    }

    @GetMapping("/member/find/password")
    public String findPassword(){

        System.out.println("get");

        return "member/find_password";

    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(
            Model model ,
            ResetPasswordInput input){

        System.out.println("post");
        boolean result = false;
        try{
         result = memberService.sendResetPassword(input);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        model.addAttribute("result",result);
        return "member/find_password_result";

    }

    @GetMapping("/member/register")
    public String register(){

        System.out.println("get");

        return "member/register";

    }
    @PostMapping("/member/register")
    public String registerSubmit(Model model, MemberInput parameter){
        System.out.println("post");

        boolean result = memberService.register(parameter);
        model.addAttribute("result",result);

        System.out.println("parameter = " + parameter.toString());

        return "member/register-complete";
    }


    @GetMapping("/member/email-auth")
    public String emailAuth(Model model,HttpServletRequest req){


        String uuid = req.getParameter("id");
        System.out.println(uuid);


        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result",result);

        return "member/email_auth";

    }

    @GetMapping("/member/info")
    public String memberInfo(){

        return "member/info";
    }

    @GetMapping("/member/reset/password")
    public String memberResetPassword(
            HttpServletRequest request
            ,Model model){

        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result",result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(ResetPasswordInput input, Model model){


        boolean result = false;
        try{

        result= memberService.resetPassword(input.getId(),input.getPassword());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        model.addAttribute("result",result);
        return "member/reset_password_result";
    }



}
/*
* http:// www.naver.com/root/idser ?id=123
* https://
* 프로토콜 //도메인 // 경로 Path
*
* */