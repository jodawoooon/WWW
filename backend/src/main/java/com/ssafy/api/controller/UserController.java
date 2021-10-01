package com.ssafy.api.controller;

import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    //user info
    @GetMapping("/mypage")
    public ResponseEntity<BaseResponseBody> readUserInfo(@RequestParam String userId){
        System.out.println("OK!");
        return new ResponseEntity<BaseResponseBody>(userService.readUserInfo(userId), HttpStatus.OK);
    }
    

    //recent course list
    @GetMapping("/mypage/recent")
    public ResponseEntity<BaseResponseBody> readRecentCourse(@RequestParam String userId){

        return new ResponseEntity<BaseResponseBody>(userService.readRecentCourse(userId), HttpStatus.OK);
    }

    //wish course list
    @GetMapping("/mypage/wish")
    public ResponseEntity<BaseResponseBody> readWishCourse(@RequestParam String userId){
        return new ResponseEntity<BaseResponseBody>(userService.readWishCourse(userId), HttpStatus.OK);
    }

    //total time
    @GetMapping("/totaltime")
    public ResponseEntity<BaseResponseBody> readTotalTime(@RequestParam String userId){
        BaseResponseBody baseResponseBody = userService.readTotalTime(userId);
        if(baseResponseBody!=null) {
            return new ResponseEntity<BaseResponseBody>(baseResponseBody, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<BaseResponseBody>(HttpStatus.NOT_FOUND);
        }
    }

    //월간/주간
    @GetMapping("/time")
    public ResponseEntity<BaseResponseBody> readTime(@RequestParam String userId,
                                                         @RequestParam String returnType){
        return new ResponseEntity<BaseResponseBody>( userService.readTime(userId,returnType), HttpStatus.OK);
    }



}
