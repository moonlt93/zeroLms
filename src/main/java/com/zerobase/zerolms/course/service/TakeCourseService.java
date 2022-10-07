package com.zerobase.zerolms.course.service;

import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.dto.TakeCourseDto;
import com.zerobase.zerolms.course.entity.TakeCourse;
import com.zerobase.zerolms.course.model.*;

import java.util.List;

public interface TakeCourseService {


    /*수강 목록*/
    List<TakeCourseDto> list(TakeCourseParam parameter);


    /*수강상태 변경*/
    ServiceResult updateStatus(long id, String status);

}
