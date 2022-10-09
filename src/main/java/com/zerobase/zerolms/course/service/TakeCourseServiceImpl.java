package com.zerobase.zerolms.course.service;

import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.dto.TakeCourseDto;
import com.zerobase.zerolms.course.entity.Course;
import com.zerobase.zerolms.course.entity.TakeCourse;
import com.zerobase.zerolms.course.entity.TakeCourseCode;
import com.zerobase.zerolms.course.mapper.CourseMapper;
import com.zerobase.zerolms.course.mapper.TakeCourseMapper;
import com.zerobase.zerolms.course.model.*;
import com.zerobase.zerolms.course.repository.CourseRepository;
import com.zerobase.zerolms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TakeCourseServiceImpl implements TakeCourseService {

    private final TakeCourseRepository takeCourseRepository;
    private final TakeCourseMapper takeCourseMapper;


    @Override
    public List<TakeCourseDto> list(TakeCourseParam parameter) {

        long totalCount = takeCourseMapper.selectListCount(parameter);
        List<TakeCourseDto> list = takeCourseMapper.selectList(parameter);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (TakeCourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }


    @Override
    public ServiceResult updateStatus(long id, String status) {

        Optional<TakeCourse> optionalTakeCourse = takeCourseRepository.findById(id);
        if(!optionalTakeCourse.isPresent()){
            return new ServiceResult(false,"수강정보가 존재하지 않습니다.");
        }
        TakeCourse takeCourse = optionalTakeCourse.get();

        takeCourse.setStatus(status);
        takeCourseRepository.save(takeCourse);


        return new ServiceResult(true);
    }

    @Override
    public List<TakeCourseDto> myCourse(String userId) {

        TakeCourseParam param = new TakeCourseParam();
        param.setUserId(userId);
        List<TakeCourseDto> list = takeCourseMapper.selectListMyCourse(param);

        return list;
    }

    /*내 강좌 한 개 처리*/
    @Override
    public TakeCourseDto detail(long id) {

        Optional<TakeCourse> OptionalTakeCourse
                = takeCourseRepository.findById(id);
        if(OptionalTakeCourse.isPresent()){
            return TakeCourseDto.of(OptionalTakeCourse.get());
        }

        return null;
    }


    @Override
    public ServiceResult cancel(long id) {

        Optional<TakeCourse> OptionalTakeCourse
                = takeCourseRepository.findById(id);
        if(!OptionalTakeCourse.isPresent()){

            return new ServiceResult(false, "수강정보가 존재하지 않습니다.");
        }

        TakeCourse takeCourse = OptionalTakeCourse.get();
        takeCourse.setStatus(TakeCourseCode.STATUS_CANCEL);
        takeCourseRepository.save(takeCourse);


        return new ServiceResult();
    }
}
