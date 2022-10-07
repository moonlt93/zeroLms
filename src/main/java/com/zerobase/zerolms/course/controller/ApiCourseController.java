package com.zerobase.zerolms.course.controller;

import com.zerobase.zerolms.admin.service.CategoryService;
import com.zerobase.zerolms.common.model.ResponseResult;
import com.zerobase.zerolms.course.model.ServiceResult;
import com.zerobase.zerolms.course.model.TakeCourseInput;
import com.zerobase.zerolms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class ApiCourseController {
/*principal, @RequestBody*/

    private final CourseService courseService;
    private final CategoryService categoryService;
    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model,
                                       Principal principal
                                       ,@RequestBody TakeCourseInput param) {
        param.setUserId(principal.getName());
 ServiceResult result = courseService.req(param);


 if(!result.isResult()){

     ResponseResult responseResult = new ResponseResult(false,result.getMessage());
     return ResponseEntity.ok().body(responseResult);
 }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }



}
