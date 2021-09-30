package com.ssafy.db.repository;

import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DongRepository extends JpaRepository<Dong, Long> {

    Optional<List<Dong>> findDongsByGugunCode(Gugun gugun);

    Optional<Dong> findDongById(Long id);
}
