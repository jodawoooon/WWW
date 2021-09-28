package com.ssafy.db.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.api.response.user.CafeBody;
import com.ssafy.api.response.user.ConvBody;
import com.ssafy.db.entity.Course;

import static com.ssafy.db.entity.QCafe.cafe;
import static com.ssafy.db.entity.QConv.conv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ConvQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<ConvBody> findConvList(Course course){
        QueryResults<ConvBody> results = queryFactory
                .select(Projections.fields(ConvBody.class,
                        conv.course.courseId.as("courseId"),
                        conv.address.as("address"),
                        conv.name.as("name"),
                        conv.latitude.as("latitude"),
                        conv.longitude.as("longitude"),
                        conv.distance.as("distance"))
                )
                .from(conv)
                .where(conv.course.courseId.eq(course.getCourseId()))
                .fetchResults();
        List<ConvBody> data = results.getResults();
        return data;
    }
}
