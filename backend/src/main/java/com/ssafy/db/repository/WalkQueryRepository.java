package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.db.entity.QUser.user;
import static com.ssafy.db.entity.QWalk.walk;

@RequiredArgsConstructor
@Repository
public class WalkQueryRepository {
    private final JPAQueryFactory queryFactory;

    // 걸은 거리 기준 유저 랭킹 3명까지
    // 추후 기간별 동적 쿼리로 개선 예정
    public List<Tuple> findTop3UserByDistance() {
        return queryFactory
                .select(user.userId.as("userId"), user.nickname.as("nickname"), walk.distance.sum().as("distance"))
                .from(walk)
                .join(walk.user, user)
                .groupBy(user.userId)
                .orderBy(walk.distance.sum().desc())
                .limit(3)
                .fetch();
    }

    //총 걸은 시간
    public List<Integer> TotalWalkTime(String userId){
        return queryFactory.select(walk.time.sum().as("totalTime"))
                .from(walk)
                .where(walk.user.userId.eq(userId))
                .fetch();
    }

    //오늘 걸은 시간
    public List<Integer> TodayWalkTime(String userId, String date){
        StringTemplate dateToString = Expressions.stringTemplate("DATE_FORMAT({0},{1})",walk.date, ConstantImpl.create("%Y-%m-%d"));
        return queryFactory.select(walk.time.sum().as("todaytime"))
                .from(walk)
                .where(walk.user.userId.eq(userId),dateToString.eq(date))
                .fetch();
    }
}
