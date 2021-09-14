package com.ssafy.db;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 이 클래스를 상속받은 entity들은 생성시간 date 컬럼 자동화

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime date;
}
