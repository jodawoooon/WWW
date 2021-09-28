package com.ssafy.api.controller;

import com.ssafy.api.request.CourseLikeReq;
import com.ssafy.api.request.CourseReq;
import com.ssafy.api.service.CourseService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 산책로 API 요청 처리를 위한 컨트롤러 정의.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/")
    @ApiOperation(value = "산책로 리스트", notes = "산책로 리스트를 가져온다")
    public ResponseEntity<BaseResponseBody> readCourseList(@RequestBody CourseReq courseReq){

        System.out.println(courseReq);
        return new ResponseEntity<BaseResponseBody>(courseService.readCourseList(courseReq), HttpStatus.OK);
    }

    @GetMapping("/")
    @ApiOperation(value = "산책로 상세 내용", notes = "선택한 산책로의 상세 내용을 가져온다")
    public ResponseEntity<BaseResponseBody> readCourseDetail(@RequestParam(defaultValue = "0") int courseId,
                                                              @RequestParam(defaultValue = "0") String userId){

        return new ResponseEntity<BaseResponseBody>(courseService.readCourseDetail(courseId, userId), HttpStatus.OK);
    }

    @PostMapping("/like")
    @ApiOperation(value = "코스 좋아요 등록", notes = "코스 좋아요 등록")
    public ResponseEntity<BaseResponseBody> createCourseLike(@RequestBody CourseLikeReq courseLikeReq) {

        return new ResponseEntity<BaseResponseBody>(courseService.createCourseLike(courseLikeReq), HttpStatus.CREATED);
    }

    @DeleteMapping("/like")
    @ApiOperation(value = "코스 좋아요 삭제", notes = "코스 좋아요 삭제")
    public ResponseEntity<BaseResponseBody> deleteCourseLike(@RequestBody CourseLikeReq courseLikeReq) {

        return new ResponseEntity<BaseResponseBody>(courseService.deleteCourseLike(courseLikeReq), HttpStatus.OK);
    }

}
