package com.ssafy.db.repository;

import com.ssafy.db.entity.Course;
import com.ssafy.db.entity.CourseLike;
import com.ssafy.db.entity.User;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseLikeRepository extends JpaRepository<CourseLike, CoursePK> {

    // 코스별 좋아요 개수 조회
    public int countByCourse(Course course);

    // 사용자가 좋아요 한 코스 전체 리스트
    public List<CourseLike> findByUser(User user);

    // (페이징) 사용자가 좋아요 한 코스 전체 리스트
    public Page<CourseLike> findByUserOrderByCourse(
            User user, Pageable pageable);

}