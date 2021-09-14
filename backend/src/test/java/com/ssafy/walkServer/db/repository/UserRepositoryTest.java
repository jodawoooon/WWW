package com.ssafy.walkServer.db.repository;

import com.querydsl.core.Tuple;
import com.ssafy.db.entity.CourseLike;
import com.ssafy.db.entity.User;
import com.ssafy.db.key.CoursePK;
import com.ssafy.db.repository.CourseLikeRepository;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.WalkQueryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseLikeRepository courseLikeRepository;

    @Autowired
    private WalkQueryRepository walkQueryRepository;

    @AfterEach
    public void cleanup() {
//        userRepository.deleteAll();
//        courseLikeRepository.deleteAll();
    }

    // user, course_like 테이블 삽입 테스트
    @Transactional
    @Test
    public void save_user() {

        String userId = "테스트 유저";
        String nickname = "테스트 닉네임";
        String name = "테스트 이름";
        String city = "테스트 시";
        String gu = "테스트 구";
        String dong = "테스트 동";

        userRepository.save(User.builder()
                .userId(userId)
                .nickname(nickname)
                .city(city)
                .gu(gu)
                .name(name)
                .dong(dong)
                .build());

        List<User> userList = userRepository.findAll();
        User user = userList.get(0);

        // "assertThat(userId).isEqualTo(user.getUserId))"과 동일 기능
        then(userId).isEqualTo(user.getUserId());

        // 외래키인 userId 컬럼은 User 객체를 사용
        // 코스 좋아요 정보 20개 삽입
        List<CourseLike> saveCourseLikeList = new ArrayList<>();
        for (int i=1; i<=20; i++) {
            CourseLike courseLike = new CourseLike();
            courseLike.setCourseId("테스트 코스"+i);
            courseLike.setUser(user);
            saveCourseLikeList.add(courseLike);
        }

        courseLikeRepository.saveAll(saveCourseLikeList);
        CoursePK id = new CoursePK(user.getUserId(), "테스트 코스1");
        Optional<CourseLike> courseLikeCheck = courseLikeRepository.findById(id);

        // course_like 테이블의 외래키인 user_id로 조인 가능한지 테스트
        String userIdCheck = courseLikeCheck.get().getUser().getUserId();
        then(user.getUserId()).isEqualTo(userIdCheck);

        // course_like 테이블의 count 조회 테스트
        int likes = courseLikeRepository.countByCourseId(saveCourseLikeList.get(0).getCourseId());
        then(1).isEqualTo(likes);

        // course_like 테이블 사용자 기준 조회
        int page = 1; int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CourseLike> courseLikePage = courseLikeRepository.findByUserOrderByCourseId(user, pageRequest);
//        System.out.println(courseLikePage.getPageable());
//        System.out.println(courseLikePage.getContent());

        // 걸은 거리 기준 유저 랭킹 3위까지 조회
//        List<Tuple> walkRanking = walkQueryRepository.findTop3UserByDistance();
//        System.out.println(walkRanking);
    }

}
