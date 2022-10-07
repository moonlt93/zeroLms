package com.zerobase.zerolms.admin.controller;

import com.zerobase.zerolms.admin.dto.MemberDto;
import com.zerobase.zerolms.admin.model.MemberAdInput;
import com.zerobase.zerolms.admin.model.MemberParam;
import com.zerobase.zerolms.course.controller.BaseController;
import com.zerobase.zerolms.member.service.MemberService;
import com.zerobase.zerolms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminMemberController extends BaseController {

    private final MemberService memberService;

    @GetMapping("/admin/member/list")
    public String list(Model model, MemberParam parameter) {

        parameter.init();
        List<MemberDto> members = memberService.list(parameter);

        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount,parameter.getPageSize(),parameter.getPageIndex(),parameter.getQueryString());

        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/member/list";
    }


    @GetMapping("/admin/member/detail")
    public String detail(Model model, MemberParam param) {

        param.init();

        MemberDto member = memberService.detail(param.getUserId());
        model.addAttribute("member", member);

        return "admin/member/detail";
    }
    @PostMapping("/admin/member/status")
    public String status(Model model, MemberAdInput param){

       boolean result= memberService.updateStatus(param.getUserStatus(),param.getUserId());


        return "redirect:/admin/member/detail?userId="+param.getUserId();
    }

    @PostMapping("/admin/member/password")
    public String updatePassword(Model model, MemberAdInput param){

        boolean result= memberService.updatePassword(param.getUserId(),param.getPassword());


        return "redirect:/admin/member/detail?userId="+param.getUserId();
    }

}
