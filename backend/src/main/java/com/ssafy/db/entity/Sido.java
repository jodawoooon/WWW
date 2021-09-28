package com.ssafy.db.entity;

import com.ssafy.db.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table(name = "sido")
public class Sido extends BaseEntity {

    String name;
}

