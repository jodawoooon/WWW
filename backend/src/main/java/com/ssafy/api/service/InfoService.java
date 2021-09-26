package com.ssafy.api.service;

import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InfoService {

    List<Sido> getSidoList();

    List<Gugun> getGugunListBySido(Long sido);

    List<Dong> getDongListByGugun(Long gugun);
}
