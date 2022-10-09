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

    /*나의 수강내역 목록*/
    List<TakeCourseDto> myCourse(String userId);


    /*수강 상세 정보*/
    TakeCourseDto detail(long id);

    /*수강신청 취소 처리*/
    ServiceResult cancel(long takeCourseId);
}
