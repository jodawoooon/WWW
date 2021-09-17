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
@Table(name="walk")
public class Walk extends BaseTimeEntity {

    @Id
    @Column(name = "walk_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int walk_id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name="course_id")
    private Course course;

    @Column
    private double distance;

    @Column
    private int time;

    @Column
    private int calorie;

}