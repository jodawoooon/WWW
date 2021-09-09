package com.ssafy.db.key;

import com.ssafy.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
    User를 제외한 테이블에서 PK를 복합키로 사용하는 클래스

    WalkRepository에서 findById를 사용할 경우 CoursePK 객체를 기본키처럼 사용
        CoursePK id = new CoursePK();
        id.setUser(user.getUserId());
        id.setCourseId("코스 아이디");
        Walk walk = walkRepository.findById(id);
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoursePK implements Serializable {
    private String user;    // 외래키로 사용시 @JoinColumn(name="user_id") 설정
    private String courseId;
}
