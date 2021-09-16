package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QCourseFinish.courseFinish;

@RequiredArgsConstructor
@Repository
public class CourseFinishQueryRepository {
    private final JPAQueryFactory queryFactory;

}
