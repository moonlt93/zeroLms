package com.zerobase.zerolms.course.mapper;


import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.dto.TakeCourseDto;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.model.TakeCourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TakeCourseMapper {

    List<TakeCourseDto> selectList(TakeCourseParam param);
    long selectListCount(TakeCourseParam param);

    List<TakeCourseDto> selectListMyCourse(TakeCourseParam param);



}
