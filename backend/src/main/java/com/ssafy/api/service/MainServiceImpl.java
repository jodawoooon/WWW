package com.ssafy.api.service;

import org.jboss.jandex.Main;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MainServiceImpl implements MainService {
    @Override
    public boolean getRank(){
        return false;
    }

    @Override
    public Date getTodayWalk(){
        return null;
    }

    @Override
    public boolean startRecord(){
        return false;
    }

    @Override
    public boolean finishRecord(){
        return false;
    }
//test for build
    @Override
    public List<String> getRecommendList(){
        return null;
    }
}
