package com.ssafy.db.repository;

import com.ssafy.db.entity.CourseLike;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseLikeRepository extends JpaRepository<CourseLike, CoursePK> {
}