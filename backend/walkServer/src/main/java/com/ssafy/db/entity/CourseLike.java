package com.ssafy.db.entity;

import com.ssafy.db.entity.key.CommonKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="course_like")
@IdClass(CommonKey.class)
public class CourseLike {

    @Id
    private String userId;

    @Id
    private String courseId;
}