package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Integer> {

    public Course findByCourseId(int courseId);

    // 주소로 코스 검색
    // "충남 홍성군 구항면 내현리 353-1"의 경우 파라미터로 "내현리" or "구향면" or "홍성군" or "충남"을 파라미터로 입력
    public Page<Course> findByAddressContainsOrderByCourseId(String address, Pageable pageable);

}