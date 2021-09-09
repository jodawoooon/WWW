package com.ssafy.db.repository;

import com.ssafy.db.entity.CourseFinish;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseFinishRepository extends JpaRepository<CourseFinish, CoursePK> {
}