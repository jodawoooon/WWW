package com.ssafy.api.service;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.model.response.user.TimeResponseBody;
import com.ssafy.common.model.response.user.TotalTimeResponseBody;
import com.ssafy.common.model.response.user.UserResponseBody;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.Walk;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.WalkQueryRepository;
import com.ssafy.db.repository.WalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    WalkQueryRepository walkQueryRepository;

    @Autowired
    WalkRepository walkRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public BaseResponseBody readUserInfo(String userId) {
        //nickname
        UserResponseBody userResponseBody = new UserResponseBody();
        String nickName = userRepository.findByUserId(userId).getNickname();

        //other
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        String today = formatter.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        LocalDate ld = LocalDate.parse(formatter.format(c.getTime()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime ldt = ld.atStartOfDay();
        int dayOfWeek = LocalDate.parse(today, DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getValue(); // monday = 1, sunday = 7

        User user = new User();
        user.setUserId(userId);

        List<Walk> ret = walkRepository.findByUserAndDateAfter(user, ldt);

        int sumTime=0,sumCalorie=0;
        double sumDistance=0;
        for(Walk w : ret){
            sumTime+=w.getTime();
            sumDistance+=w.getDistance();
            sumCalorie+=w.getCalorie();
        }

        userResponseBody.setStatusCode(200);
        userResponseBody.setMessage("OK");
        userResponseBody.setNickName(nickName);
        userResponseBody.setSumCalorie(sumCalorie);
        userResponseBody.setWeeklySumTime(sumTime);
        userResponseBody.setSumDistance(sumDistance);
        userResponseBody.setDailyAvgTime(sumTime/dayOfWeek);

        return userResponseBody;

    }

    @Override
    public BaseResponseBody readRecentCourse(String userId) {
        return null;
    }

    @Override
    public BaseResponseBody readWishCourse(String userId) {

        return null;
    }

    @Override
    public BaseResponseBody readTotalTime(String userId) {

        try {
            int time = -1;
            List<Integer> result = walkQueryRepository.TotalWalkTime(userId);
            if(result.get(0)!=null)time=result.get(0);
            System.out.println(time);
            if (time != -1) {
                return new TotalTimeResponseBody(200, "OK", time);
            } else {
                return new TotalTimeResponseBody(404, "Not Found", time);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public BaseResponseBody readTime(String userId, String type) {

        TimeResponseBody timeResponseBody = new TimeResponseBody();

        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        int today=0;
        if("week".equals(type)){
            today = LocalDate.parse(formatter.format(c.getTime()),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getValue(); // monday = 1,
            c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        }
        else if("month".equals(type)){
            today = LocalDate.now().getDayOfMonth();
            c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        }

        LocalDate ld = LocalDate.parse(formatter.format(c.getTime()), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime ldt = ld.atStartOfDay();

        User user = new User();

        user.setUserId(userId);

        List<Walk> ret = walkRepository.findByUserAndDateAfter(user, ldt);

        int sumTime=0,sumCalorie=0;
        double sumDistance=0;
        for(Walk w : ret){
            sumTime+=w.getTime();
            sumDistance+=w.getDistance();
            sumCalorie+=w.getCalorie();
        }

        timeResponseBody.setStatusCode(200);
        timeResponseBody.setMessage("OK");
        timeResponseBody.setSumTime(sumTime);
        timeResponseBody.setAvgTime((double)sumTime/today);
        timeResponseBody.setSumCalorie(sumCalorie);
        timeResponseBody.setAvgCalorie((double)sumCalorie/today);
        timeResponseBody.setSumDistance(sumDistance);
        timeResponseBody.setAvgDistance(sumDistance/today);


        return timeResponseBody;
    }
}
