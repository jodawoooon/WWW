package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QCourseFinish.courseFinish;

@RequiredArgsConstructor
@Repository
public class CourseFinishQueryRepository {
    private final JPAQueryFactory queryFactory;

    //가장 많이 산책한 코스 top 5
    public List<Integer> findTop5CourseByCnt(String sigu){
        return queryFactory
                .select(courseFinish.course.courseId.as("course"))
                .from(courseFinish)
                .join(courseFinish.course,course)
                .where(course.address.contains(sigu))
                .groupBy(courseFinish.course)
                .orderBy(courseFinish.course.count().desc())
                .limit(5)
                .fetch();
    }
}
