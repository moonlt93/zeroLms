package com.zerobase.zerolms.course.repository;

import com.zerobase.zerolms.course.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<List<Course>> findByCategoryId(long categoryId);



}
