package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
                        course.time.as("courseTime")
                        )
                .from(courseLike)
                .join(courseLike.course, course)
                .where(courseLike.user.userId.eq(userId))
                .fetch();
    }


}
