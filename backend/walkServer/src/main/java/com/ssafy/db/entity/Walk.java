package com.ssafy.db.entity;

import com.ssafy.db.entity.key.WalkKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="walk")
@IdClass(WalkKey.class)
public class Walk {

    @Id
    private int walkId;

    @Id
    private String userId;

    @Id
    private String courseId;

    @Column
    private float distance;

    @Column
    private int time;

    @Column
    private int calorie;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;


}