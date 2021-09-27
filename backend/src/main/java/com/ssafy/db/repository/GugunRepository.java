package com.ssafy.db.repository;

import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GugunRepository extends JpaRepository<Gugun, Long> {

    Optional<List<Gugun>> findGugunsBySidoCode(Sido sido);
}
