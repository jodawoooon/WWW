package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.db.entity.QCourse.course;
import static com.ssafy.db.entity.QWalk.walk;

@RequiredArgsConstructor
@Repository
public class CourseQueryRepository {
    private final JPAQueryFactory queryFactory;


    //최근 걸은 course
    public List<Tuple> findRecentList(String userId) {
        return queryFactory
                .select(course.courseId.as("courseId"),
                        course.name.as("courseName"),
                        course.address.as("address"),
                        course.distance.as("courseLength"),
                        course.time.as("courseTime")
                )
                .from(walk)
                .join(walk.course,course)
                .where(walk.user.userId.eq(userId))
                .orderBy(walk.date.desc())
                .fetch();
    }
}
