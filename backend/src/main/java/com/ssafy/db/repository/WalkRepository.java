package com.ssafy.db.repository;

import com.ssafy.db.entity.Walk;
import com.ssafy.db.key.CoursePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkRepository extends JpaRepository<Walk, CoursePK> {
}