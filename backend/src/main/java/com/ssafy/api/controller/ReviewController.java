package com.ssafy.api.controller;

import com.ssafy.api.request.CourseReviewReq;
import com.ssafy.api.service.ReviewService;
import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/")
    public ResponseEntity<BaseResponseBody> createReview(@RequestBody CourseReviewReq courseReviewReq){

        return new ResponseEntity<BaseResponseBody>(reviewService.createReview(courseReviewReq), HttpStatus.CREATED);
    }


    @PutMapping("/")
    public ResponseEntity<BaseResponseBody> updateReview(@RequestBody CourseReviewReq courseReviewReq){

        return new ResponseEntity<BaseResponseBody>(reviewService.updateReview(courseReviewReq), HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<BaseResponseBody> deleteReview(@RequestBody CourseReviewReq courseReviewReq){

        return new ResponseEntity<BaseResponseBody>(reviewService.deleteReview(courseReviewReq), HttpStatus.OK);
    }
}
