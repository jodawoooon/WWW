package com.ssafy.db.repository;

import com.querydsl.core.Tuple;
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
                .orderBy(walk.distance.sum().asc())
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

}
