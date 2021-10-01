package com.ssafy.db.repository;

import com.ssafy.db.entity.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SidoRepository extends JpaRepository<Sido, Long> {

    Optional<Sido> findSidoById(Long id);
}
