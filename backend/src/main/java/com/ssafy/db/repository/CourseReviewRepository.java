package com.ssafy.db.repository;

import com.ssafy.db.entity.CourseReview;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseReviewRepository extends JpaRepository<CourseReview, CoursePK> {
}