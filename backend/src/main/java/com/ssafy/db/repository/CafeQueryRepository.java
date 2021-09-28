package com.ssafy.db.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.user.CafeBody;
import com.ssafy.db.entity.Course;
import static com.ssafy.db.entity.QCafe.cafe;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Projection;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CafeQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<CafeBody> findCafeList(Course course){
        QueryResults<CafeBody> results = queryFactory
                .select(Projections.fields(CafeBody.class,
                                cafe.course.courseId.as("courseId"),
                                cafe.address.as("address"),
                                cafe.name.as("name"),
                                cafe.latitude.as("latitude"),
                                cafe.longitude.as("longitude"),
                                cafe.distance.as("distance"))
                        )
                        .from(cafe)
                        .where(cafe.course.courseId.eq(course.getCourseId()))
                        .fetchResults();
        List<CafeBody> data = results.getResults();
        return data;
    }
}
