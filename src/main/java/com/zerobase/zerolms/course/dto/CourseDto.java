package com.zerobase.zerolms.course.dto;

import com.zerobase.zerolms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseDto {

    Long id;
    long categoryId;
    String imagePath;
    String keyword;
    String subject;
    String summary;
    String contents;
    long price;
    long salePrice;
    LocalDate saleEndDt;
    LocalDateTime regDt;
    LocalDateTime udtDt;


    //페이징
    Long totalCount;
    long seq;

    //filename
    String filename;
    String urlFilename;


    public static CourseDto of(Course course) {
        return   CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDt(course.getSaleEndDt())
                .regDt(course.getRegDt())
                .udtDt(course.getUdtDt())
                .filename(course.getFileName())
                .urlFilename(course.getUrlFileName())
                .build();

    }

    public static List<CourseDto> of(List<Course> course){

        if(course == null){
            return null;
        }


        List<CourseDto> courseList = new ArrayList<>();
        for (Course x : course) {
            courseList.add(CourseDto.of(x));
        }
        return courseList;
        }


}
