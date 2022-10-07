package com.zerobase.zerolms.course.service;

import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.model.CourseInput;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.model.ServiceResult;
import com.zerobase.zerolms.course.model.TakeCourseInput;

import java.util.List;

public interface CourseService {

    boolean add(CourseInput param);

    /*강좌 목록*/
    List<CourseDto> list(CourseParam parameter);

    /*강좌 상세*/
    CourseDto getById(long id);
    /*강좌 수정*/
    boolean set(CourseInput param);

    /*강좌 내용 삭제 */
    boolean del(String idList);


    /*강좌 리스트 관리자 화면과 다름*/
    List<CourseDto> frontList(CourseParam parameter);

    /*사용자 강좌 상세 정보 조회*/
    CourseDto frontDetail(long id);

    /*수강신청 */
    ServiceResult req(TakeCourseInput param);
}
