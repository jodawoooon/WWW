package com.ssafy.api.controller;

import com.ssafy.api.service.ReviewService;
import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/{courseId}")
    public ResponseEntity<BaseResponseBody> readReviewAverage(@PathVariable int courseId){
        reviewService.readReviewAverage(courseId);

        return null;
    }

    @PostMapping
    public ResponseEntity<BaseResponseBody> createReview(){
        reviewService.createReview();

        return null;
    }


    @PutMapping
    public ResponseEntity<BaseResponseBody> updateReview(){
        reviewService.updateReview();

        return null;
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<BaseResponseBody> deleteReview(@PathVariable int courseId){
        reviewService.deleteReview(courseId);

        return null;
    }
}
