package com.ssafy.api.controller;

import com.ssafy.api.response.CourseDataGetRes;
import com.ssafy.api.response.CourseListGetRes;
import com.ssafy.api.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 산책로 API 요청 처리를 위한 컨트롤러 정의.
 */
@CrossOrigin(origins={"*"}, maxAge=6000)
@Api(value = "산책로 정보 API", tags = {"Course"})
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("")
    @ApiOperation(value = "산책로 리스트", notes = "산책로 리스트를 가져온다")
    @ApiResponses({
            @ApiResponse(code = 204, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<CourseListGetRes> courseList(){
        List<CourseDataGetRes> courseList = courseService.getCourseList();
        return ResponseEntity.ok(CourseListGetRes.of(200, "Success", courseList, courseList.size()));
    }
}
