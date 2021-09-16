package com.ssafy.db.repository;

import com.ssafy.db.entity.CourseReview;
import com.ssafy.db.entity.User;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseReviewRepository extends JpaRepository<CourseReview, CoursePK> {

    // 사용자가 리뷰한 코스 전체 리스트
    public List<CourseReview> findByUser(User user);

    // (페이징) 사용자가 리뷰한 코스 전체 리스트
    public Page<CourseReview> findByUserOrderByCourse(
            User user, Pageable pageable);

}