package com.zerobase.zerolms.member.controller;

import com.zerobase.zerolms.admin.dto.MemberDto;
import com.zerobase.zerolms.course.dto.TakeCourseDto;
import com.zerobase.zerolms.course.model.ServiceResult;
import com.zerobase.zerolms.course.service.TakeCourseService;
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
import java.security.Principal;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MemberController  {

   private final MemberService memberService;
   private final TakeCourseService takeCourseService;


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
    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal){

        String userId= principal.getName();

       MemberDto detail= memberService.detail(userId);

       model.addAttribute("detail",detail);
        return "member/info";

    }

    @PostMapping("/member/info")
    public String memberInfo(Model model,MemberInput param, Principal principal){

        String userId = principal.getName();
        param.setUserId(userId);

        ServiceResult result = memberService.updateMember(param);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }
        return "redirect:/member/info";
    }
    @GetMapping("/member/password")
    public String memberPassword(Model model, Principal principal) {

        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail", detail);

        return "member/password";
    }
    @PostMapping("/member/password")
    public String memberPasswordSubmit(Model model
            , MemberInput parameter
            , Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberPassword(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }

    @GetMapping("/member/MytakeCourse")
    public String memberTakeCourse(Model model, Principal principal){

        String userId = principal.getName();
        List<TakeCourseDto> list = takeCourseService.myCourse(userId);

        model.addAttribute("list", list);
        return "member/MytakeCourse";

    }



}
/*
* http:// www.naver.com/root/idser ?id=123
* https://
* 프로토콜 //도메인 // 경로 Path
*
* */