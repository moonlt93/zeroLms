package com.zerobase.zerolms.course.mapper;


import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {

    List<CourseDto> selectList(CourseParam param);
    long selectListCount(CourseParam param);



}
