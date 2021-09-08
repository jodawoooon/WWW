package com.ssafy.api.controller;

import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
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
    public ResponseEntity<BaseResponseBody> readUserInfo(@RequestParam int userId){
        userService.readUserInfo(userId);
        return null;
    }

    //recent course list
    @GetMapping("/mypage/recent")
    public ResponseEntity<BaseResponseBody> readRecentCourse(@RequestParam int userId){
        userService.readRecentCourse(userId);

        return null;
    }

    //wish course list
    @GetMapping("/mypage/wish")
    public ResponseEntity<BaseResponseBody> readWishCourse(@RequestParam int userId){
        userService.readWishCourse(userId);

        return null;
    }

    //total time
    @GetMapping("/totaltime")
    public ResponseEntity<BaseResponseBody> readTotalTime(@RequestParam int userId){
        userService.readTotalTime(userId);

        return null;
    }

    //월간/주간
    @GetMapping("/time")
    public ResponseEntity<BaseResponseBody> readTime(@RequestParam int userId,
                                                          @RequestParam String date, @RequestParam String type){
        //date
        //type : "month / week"

        userService.readTime(userId,date,type);

        return new ResponseEntity<BaseResponseBody>(new BaseResponseBody(200, "msg"), HttpStatus.OK);
    }

}
