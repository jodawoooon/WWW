package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="course")
public class Course {
    @Id
    @Column(length = 50)
    private String courseId;

    @Column(length = 50)
    private String flagName;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String route;

    @Column(length = 50)
    private String title;

    @Column
    private float distance;

    @Column(length = 50)
    private String level;
    @Column(length = 50)
    private String time;
    @Column(length = 50)
    private String option;
    @Column(length = 50)
    private String toilet;
    @Column(length = 50)
    private String conveient;
    @Column
    private float latitude;
    @Column
    private float lontutude;
    @Column(length = 50)
    private String address;

}