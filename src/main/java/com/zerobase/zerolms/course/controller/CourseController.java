package com.zerobase.zerolms.course.controller;

import com.zerobase.zerolms.admin.dto.CategoryDto;
import com.zerobase.zerolms.admin.service.CategoryService;
import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;
    @GetMapping("/course")
    public String courseList(Model model, CourseParam parameter) {

        List<CourseDto> list= courseService.frontList(parameter);
        model.addAttribute("list",list);

        List<CategoryDto> categoryList= categoryService.frontList(CategoryDto.builder().build());

        int courseTotalCount =0;
        if(categoryList != null){
            for(CategoryDto x : categoryList){
                courseTotalCount+= x.getCourseCount();
            }
        }

        model.addAttribute("categoryList", categoryList);
        model.addAttribute("courseTotalCount", courseTotalCount);

        return "course/index";
    }

    @GetMapping("/course/{id}")
    public String CourseDetail(Model model, CourseParam parameter) {


     CourseDto detail=  courseService.frontDetail(parameter.getId());

     model.addAttribute("detail",detail);


        return "course/detail";
    }



}
