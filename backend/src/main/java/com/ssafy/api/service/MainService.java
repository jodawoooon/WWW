package com.ssafy.api.service;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface MainService {

    public boolean getRank();

    public Date getTodayWalk();

    public boolean startRecord();

    public boolean finishRecord();

    public List<String> getRecommendList();
}
