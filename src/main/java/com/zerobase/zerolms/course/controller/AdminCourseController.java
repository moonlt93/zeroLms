package com.zerobase.zerolms.course.controller;

import com.zerobase.zerolms.admin.service.CategoryService;
import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseInput;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class AdminCourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;
    @GetMapping("/admin/course/list")
    public String courseList(Model model, CourseParam parameter) {

        parameter.init();
        List<CourseDto> courseList = courseService.list(parameter);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(courseList)){
            totalCount = courseList.get(0).getTotalCount();
        }


        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount,parameter.getPageSize(),parameter.getPageIndex(),parameter.getQueryString());

        model.addAttribute("list", courseList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager",pagerHtml);



        return "admin/course/list";
    }

    @GetMapping(value={"/admin/course/add","/admin/course/edit"})
    public String add(Model model, HttpServletRequest req
    ,CourseInput param) {


        model.addAttribute("category",categoryService.selectList());

        //category 정보 필요
        boolean isEdit = req.getRequestURI().contains("/edit");
        CourseDto detail = new CourseDto();

        if(isEdit){
            long id = param.getId();
            CourseDto existCourse = courseService.getById(id);
            if(existCourse == null){
            model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }

            detail = existCourse;
        }
        model.addAttribute("isEdit",isEdit);
        model.addAttribute("detail",detail);

        return "admin/course/add";
    }
    @PostMapping(value = {"/admin/course/add","/admin/course/edit"})
    public String addSubmit(Model model, CourseInput param
    ,HttpServletRequest req) {

        boolean isEdit = req.getRequestURI().contains("/edit");

        if(isEdit){
            long id = param.getId();

            CourseDto existCourse = courseService.getById(id);
            if(existCourse == null){
                model.addAttribute("message","강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = courseService.set(param);
        }else{
            boolean result = courseService.add(param);
        }

        return "redirect:/admin/course/list";
    }



    @PostMapping("/admin/course/delete")
    public String deleteSubmit(Model model, CourseInput param
            ,HttpServletRequest req) {

        System.out.println(param.getIdList());
        boolean result = courseService.del(param.getIdList());


        return "redirect:/admin/course/list";
    }


}
