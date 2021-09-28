package com.ssafy.db.entity;

import com.ssafy.db.key.FacilityPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FacilityPK.class)
@Table(name = "cafe")
public class Cafe {

    // 산책로 id(9476)
    @Id
    @ManyToOne(targetEntity = Course.class)
    @JoinColumn(name="course_id")
    private Course course;

    // 카페 주소 (경기 의왕시 내손동 668)
    @Id
    private String address;

    // 카페 이름 (GS25 내손공원점)
    @Column(name = "name")
    private String name;

    // 위도 36.5714867
    @Column(name = "latitude")
    private double latitude;

    // 경도 126.6168424
    @Column(name = "longitude")
    private double longitude;

    // 산책로와의 거리 (856.384) M 단위
    @Column(name = "distance")
    private double distance;
}