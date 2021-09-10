package com.ssafy.walkServer.db.repository;

import com.ssafy.db.entity.CourseLike;
import com.ssafy.db.entity.User;
import com.ssafy.db.key.CoursePK;
import com.ssafy.db.repository.CourseLikeRepository;
import com.ssafy.db.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;

//DB table 수정 부탁
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseLikeRepository courseLikeRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
        courseLikeRepository.deleteAll();
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
                .nickName(nickname)
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
        CourseLike courseLike = new CourseLike();
        courseLike.setCourseId("테스트 코스");
        courseLike.setUser(user);

        courseLikeRepository.save(courseLike);
        CoursePK id = new CoursePK(user.getUserId(), "테스트 코스");
        Optional<CourseLike> courseLikeCheck = courseLikeRepository.findById(id);

        // course_like 테이블의 외래키인 user_id로 조인 가능한지 테스트
        String userIdCheck = courseLikeCheck.get().getUser().getUserId();
        then(user.getUserId()).isEqualTo(userIdCheck);
        
    }

}
