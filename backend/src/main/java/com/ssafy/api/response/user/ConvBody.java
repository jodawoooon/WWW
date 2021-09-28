package com.ssafy.api.response.user;

import lombok.Data;

@Data
public class ConvBody {
    int courseId;
    String address;
    String name;
    double latitude;
    double longitude;
    double distance;
}
