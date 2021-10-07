package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Course;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.db.entity.QUser.user;
import static com.ssafy.db.entity.QWalk.walk;
import static com.ssafy.db.entity.QCourseLike.courseLike;
import static com.ssafy.db.entity.QCourse.course;

@RequiredArgsConstructor
@Repository
public class CourseLikeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Tuple> findWishList(String userId) {
        return queryFactory
                .select(course.courseId.as("courseId"),
                        course.name.as("courseName"),
                        course.address.as("address"),
                        course.distance.as("courseLength"),
                        course.time.as("courseTime"),
                        course.timeInt.as("timeInt"),
                        course.flagName.as("flagName"),
                        course.latitude.as("latitude"),
                        course.longitude.as("longitude")
                        )
                .from(courseLike)
                .join(courseLike.course, course)
                .where(courseLike.user.userId.eq(userId))
                .fetch();
    }
}
