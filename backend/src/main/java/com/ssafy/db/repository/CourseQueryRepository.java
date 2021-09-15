package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CourseQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

}
