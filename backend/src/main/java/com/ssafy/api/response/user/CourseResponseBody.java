package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CourseResponseBody extends BaseResponseBody {
    List<CourseBody> courseList;
    int page;
    int totalPage;
    boolean hasNextPage; // 무한 스크롤로 구현할시 위의 page, totalPage 삭제 예정

    public CourseResponseBody(){
        super();
        courseList=new ArrayList<>();
    }
}
