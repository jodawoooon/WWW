package com.ssafy.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name="user")
public class User {
    @Id
    @Column(length = 50, name = "user_id")
    private String userId;

    @Column(length = 50)
    private String nickname;

    @Column(length = 50)
    private String name;
    
    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String gu;

    @Column(length = 50)
    private String dong;

}