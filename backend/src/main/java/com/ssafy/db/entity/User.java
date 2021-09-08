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
@Table(name="user")
public class User {
    @Id
    @Column(length = 50)
    private String userId;

    @Column(length = 50)
    private String nickName;

    @Column(length = 50)
    private String name;
    
    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String gu;

    @Column(length = 50)
    private String dong;
}