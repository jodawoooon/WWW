package com.ssafy.api.response.user;

import com.ssafy.common.model.response.BaseResponseBody;

import java.util.ArrayList;
import java.util.List;

public class WishResponseBody extends BaseResponseBody {

    List<Integer> list;
    int size;
    public WishResponseBody(){
        list= new ArrayList<>();
        size=0;
    }
    public WishResponseBody(List<Integer> l){
        list=l;
        size=list.size();
    }
    public WishResponseBody(Integer statusCode, String message, List<Integer> l){
        super(statusCode, message);
        list=l;
        size=list.size();
    }

}
