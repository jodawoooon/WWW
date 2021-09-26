package com.ssafy.db.entity;

import com.ssafy.db.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Table(name = "dong")
public class Dong extends BaseEntity {
    String name;

    @ManyToOne
    @JoinColumn(name="gugun_code")
    private Gugun gugunCode;
}
