package com.ssafy.db.repository;

import com.ssafy.db.entity.CourseFinish;
import com.ssafy.db.entity.User;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseFinishRepository extends JpaRepository<CourseFinish, CoursePK> {

    // 사용자가 완주한 코스 전체 리스트
    public List<CourseFinish> findByUser(User user);

    // (페이징) 사용자가 완주한 코스 전체 리스트
    public Page<CourseFinish> findByUserOrderByCourseId(
            User user, Pageable pageable);

}