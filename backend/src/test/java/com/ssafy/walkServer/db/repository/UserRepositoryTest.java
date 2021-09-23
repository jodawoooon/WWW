package com.ssafy.walkServer.db.repository;

import com.querydsl.core.Tuple;
import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.CourseLike;
import com.ssafy.db.entity.User;
import com.ssafy.db.key.CoursePK;
import com.ssafy.db.repository.CourseLikeRepository;
import com.ssafy.db.repository.CourseRepository;
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
    private CourseRepository courseRepository;

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

        Course course = Course.builder()
                .courseId(2)
                .flagName("거북이 마을 솔바람길")
                .name("01코스")
                .route("전통체험관~보살바위~말바위~자라바위~전용석고택~호랑이가 잡아준묘~사랑바위~전통체험관(2.1km)")
                .distance(2.1)
                .level("보통")
                .time("1시간 10분")
                .timeInt(70)
                .detail("마을의 모양이 거북이의 목처럼 생겨 구목(구을목)이라고 하고, 거북모양의 바위가 머리를 안쪽으로 향하고 있어 내현이라고 한다. 아홉가지의 보물을 덮고 있다는 보개산의 아늑함을 배경으로 500년 이상 수령의 느티나무, 철따라 피고지는 야생화, 군데 군데 위용을 드러내는 바위, 고풍스러운 전통가옥이 어우러져 한폭의 동양화 같은 천혜의 자연환경을 갖추고 있다.『동창이 밝았느냐 노고지리 우지진다-권농가』의 저자인 남구만 선생이 사신곳으로 농촌전통테마마을과 국내 최초 농어촌인성학교로 지정 되어 많은 이들의 발길을 옮기고 있다.")
                .option("전통체험관")
                .convStore("홍성버스터미널 매점")
                .toilet("전통체험관, 마을화장실")
                .address("충남 홍성군 구항면 내현리 353-1")
                .latitude(36.5714867)
                .longitude(126.6168424)
                .build();

        courseRepository.save(course);

        // 외래키인 userId 컬럼은 User 객체를 사용
        // 코스 좋아요 정보 삽입
        CourseLike courseLike = new CourseLike();
        courseLike.setCourse(course);
        courseLike.setUser(user);

        courseLikeRepository.save(courseLike);
        CoursePK id = new CoursePK(user.getUserId(), course.getCourseId());
        Optional<CourseLike> courseLikeCheck = courseLikeRepository.findById(id);

        // course_like 테이블의 외래키인 user_id로 조인 가능한지 테스트
        String userIdCheck = courseLikeCheck.get().getUser().getUserId();
        then(user.getUserId()).isEqualTo(userIdCheck);

        // course_like 테이블의 count 조회 테스트
        int likes = courseLikeRepository.countByCourse(courseLike.getCourse());
        then(1).isEqualTo(likes);

        // course_like 테이블 사용자 기준 조회
        int page = 1; int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<CourseLike> courseLikePage = courseLikeRepository.findByUserOrderByCourse(user, pageRequest);
//        System.out.println(courseLikePage.getPageable());
//        System.out.println(courseLikePage.getContent());

        // 걸은 거리 기준 유저 랭킹 3위까지 조회
//        List<Tuple> walkRanking = walkQueryRepository.findTop3UserByDistance();
//        System.out.println(walkRanking);
    }

}
