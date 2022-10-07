package com.zerobase.zerolms.course.controller;

import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.model.CategoryInput;
import com.zerobase.zerolms.admin.model.MemberParam;
import com.zerobase.zerolms.admin.service.CategoryService;
import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.dto.TakeCourseDto;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.model.ServiceResult;
import com.zerobase.zerolms.course.model.TakeCourseParam;
import com.zerobase.zerolms.course.service.CourseService;
import com.zerobase.zerolms.course.service.TakeCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminTakeCourseController extends BaseController {

    private final TakeCourseService takeCourseService;

    @GetMapping("/admin/takeCourse/list")
    public String takeList(Model model, TakeCourseParam param) {
        param.init();
        List<TakeCourseDto> courseList = takeCourseService.list(param);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(courseList)){
            totalCount = courseList.get(0).getTotalCount();
        }


        String queryString = param.getQueryString();
        String pagerHtml = getPagerHtml(totalCount,param.getPageSize(),param.getPageIndex(),param.getQueryString());

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager",pagerHtml);



        return "admin/takeCourse/list";

    }

    @PostMapping("/admin/takeCourse/status")
    public String takeStatus(Model model, TakeCourseParam param) {
        param.init();
        ServiceResult result = takeCourseService.updateStatus(param.getId(),param.getStatus());

        if(!result.isResult()){
            model.addAttribute("message",result.getMessage());
            return "common/error";
        }



        return "redirect:/admin/takeCourse/list";

    }


}
