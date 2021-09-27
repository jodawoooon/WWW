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
@Table(name = "gugun")
public class Gugun extends BaseEntity{
    String name;

    @ManyToOne
    @JoinColumn(name="sido_code")
    private Sido sidoCode;

}
