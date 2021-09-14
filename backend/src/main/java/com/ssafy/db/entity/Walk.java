package com.ssafy.db.entity;

import com.ssafy.db.BaseTimeEntity;
import com.ssafy.db.key.CoursePK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@IdClass(CoursePK.class)
@Table(name="walk")
public class Walk extends BaseTimeEntity {

    @Id
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @Id
    @Column(length = 50, name = "course_id")
    private String courseId;

    @Column
    private float distance;

    @Column
    private int time;

    @Column
    private int calorie;

}