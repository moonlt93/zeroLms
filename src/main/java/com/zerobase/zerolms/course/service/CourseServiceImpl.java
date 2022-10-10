package com.zerobase.zerolms.course.service;

import com.zerobase.zerolms.course.dto.CourseDto;
import com.zerobase.zerolms.course.entity.Course;
import com.zerobase.zerolms.course.entity.TakeCourse;
import com.zerobase.zerolms.course.entity.TakeCourseCode;
import com.zerobase.zerolms.course.mapper.CourseMapper;
import com.zerobase.zerolms.course.model.CourseInput;
import com.zerobase.zerolms.course.model.CourseParam;
import com.zerobase.zerolms.course.model.ServiceResult;
import com.zerobase.zerolms.course.model.TakeCourseInput;
import com.zerobase.zerolms.course.repository.CourseRepository;
import com.zerobase.zerolms.course.repository.TakeCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;
    private final TakeCourseRepository takeCourseRepository;

    private LocalDate getLocalDate(String value){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try{
          return LocalDate.parse(value,formatter);
        }catch (Exception e){
        }
            return null;
    }


    @Override
    public boolean set(CourseInput param) {
        LocalDate saleEndDt = getLocalDate(param.getSaleEndDtText());
        Optional<Course> optionalCourse = courseRepository.findById(param.getId());

        if(!optionalCourse.isPresent()){
            return false;
        }
    Course course = optionalCourse.get();
        course.setContents(param.getContents());
        course.setKeyword(param.getKeyword());
        course.setSummary(param.getSummary());
        course.setPrice(param.getPrice());
        course.setSalePrice(param.getSalePrice());
        course.setCategoryId(param.getCategoryId());
        course.setSubject(param.getSubject());
        course.setSaleEndDt(saleEndDt);
        course.setUdtDt(LocalDateTime.now());
        course.setFileName(param.getFileName());
        course.setUrlFileName(param.getUrlFileName());
        courseRepository.save(course);

       return true;

    }

    @Override
    public boolean add(CourseInput param) {

        LocalDate saleEndDt = getLocalDate(param.getSaleEndDtText());
        Course course = Course.builder()
                .keyword(param.getKeyword())
                .summary(param.getSummary())
                .price(param.getPrice())
                .contents(param.getContents())
                .salePrice(param.getSalePrice())
                .categoryId(param.getCategoryId())
                .subject(param.getSubject())
                .regDt(LocalDateTime.now())
                .saleEndDt(saleEndDt)
                .fileName(param.getFileName())
                .urlFileName(param.getUrlFileName())
                //종료일
                .build();

        courseRepository.save(course);



        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam parameter) {

        long totalCount = courseMapper.selectListCount(parameter);
        List<CourseDto> list =courseMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i=0;
            for (CourseDto x:list) {
                x.setTotalCount(totalCount);
                x.setSeq( totalCount-parameter.getPageStart()-i);
                i++;
            }
        }

        return list;
    }

    @Override
    public CourseDto getById(long id) {

      return  courseRepository.findById(id).map(CourseDto :: of)
                .orElse(null);


    }

    @Override
    public boolean del(String idList) {
       if(idList != null && idList.length()>0){
           String[] ids = idList.split(",");
           for(String x : ids){
               long id =0L;
               try{
                   id=Long.parseLong(x);
               }catch (Exception e){

               }
               if(id>0){
                   courseRepository.deleteById(id);
               }
           }
       }
       return true;

    }


    @Override
    public List<CourseDto> frontList(CourseParam parameter) {

        if(parameter.getCategoryId()<1){

        List<Course> CourseList = courseRepository.findAll();
            return CourseDto.of(CourseList);
        }
      /*  return courseRepository.findByCategoryId(parameter.getCategoryId()).map(CourseDto::of).orElse(null);

*/   Optional<List<Course>> optionalList = courseRepository.findByCategoryId(parameter.getCategoryId());
       if(optionalList.isPresent()){
           return CourseDto.of(optionalList.get()) ;
       }
        return null;
    }

    @Override
    public CourseDto frontDetail(long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            return CourseDto.of(optionalCourse.get());
        }
        return null;
    }

    @Override
    public ServiceResult req(TakeCourseInput param) {

        ServiceResult result = new ServiceResult();



        Optional<Course> optionalCourse = courseRepository.findById(param.getCourseId());
        if(!optionalCourse.isPresent()){

        result.setResult(false);
        result.setMessage("강좌정보가 존재하지 않습니다.");
            return result;
        }
        Course course = optionalCourse.get();

        //이미 신청했다면?

        String [] statusList = {TakeCourse.STATUS_REQ,TakeCourse.STATUS_COMPLETE};
        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(course.getId(),
                param.getUserId(), Arrays.asList(statusList));

        if(count > 0){
            result.setResult(false);
            result.setMessage("이미 신청한 강좌정보가 존재합니다.");
        return result;
        }


        TakeCourse takeCourse = TakeCourse.builder()
                .courseId(course.getId())
                .userId(param.getUserId())
                .payPrice(course.getSalePrice())
                .regDt(LocalDateTime.now())
                .status(TakeCourse.STATUS_REQ)
                .build();
        takeCourseRepository.save(takeCourse);

       result.setResult(true);
       result.setMessage("");
       return result;
    }

    @Override
    public List<CourseDto> listAll() {


        List<Course> courseList= courseRepository.findAll();
        return CourseDto.of(courseList);

    }
}
