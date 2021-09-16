package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "flag_name")
    private String flagName;

    @Column
    private String name;

    @Column(length = 4000)
    private String route;

    @Column
    private double distance;

    @Column
    private String level;

    @Column
    private String time;

    @Column(length = 4000)
    private String detail;

    @Column(length = 500)
    private String option;

    @Column(length = 500)
    private String toilet;

    @Column(name = "conv_store")
    private String convStore;

    @Column
    private double latitude;

    @Column
    private double longtitude;

    @Column(length = 200)
    private String address;

}