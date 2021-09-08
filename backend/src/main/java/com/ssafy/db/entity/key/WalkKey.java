package com.ssafy.db.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalkKey implements Serializable {

    int walkId;
    String userId;
    String courseId;


}
