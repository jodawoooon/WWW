package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.Dong;
import com.ssafy.db.entity.Gugun;
import com.ssafy.db.entity.Sido;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.DongRepository;
import com.ssafy.db.repository.GugunRepository;
import com.ssafy.db.repository.SidoRepository;
import com.ssafy.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    SidoRepository sidoRepository;

    @Autowired
    GugunRepository gugunRepository;

    @Autowired
    DongRepository dongRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Sido> getSidoList() {
        return sidoRepository.findAll();
    }

    @Override
    public List<Gugun> getGugunListBySido(Long sido) {
        Optional<List<Gugun>> gugunList = gugunRepository.findGugunsBySidoCode(sidoRepository.findById(sido).get());
        if(gugunList.isPresent()){
            return gugunList.get();
        }
        return null;

    }

    @Override
    public List<Dong> getDongListByGugun(Long gugun) {
        Optional<List<Dong>> dongList = dongRepository.findDongsByGugunCode(gugunRepository.findById(gugun).get());
        if(dongList.isPresent()){
            return dongList.get();
        }
        return null;
    }

    @Override
    public void register(UserRegisterPostReq userRegistPostReq) {
        User user = new User();

        System.out.println(userRegistPostReq);

        user.setUserId(userRegistPostReq.getUserId());
        user.setNickname(userRegistPostReq.getNickname());
        user.setName(userRegistPostReq.getName());

        if(userRegistPostReq.getCity() != null){
            Long sidoId = Long.parseLong(userRegistPostReq.getCity());
            Sido sido = sidoRepository.findSidoById(sidoId).get();
            user.setCity(sido.getName());
        }

        if(userRegistPostReq.getGu() != null){
            Long gugunId = Long.parseLong(userRegistPostReq.getGu());
            Gugun gugun = gugunRepository.findGugunById(gugunId).get();
            user.setGu(gugun.getName());
        }

        if(userRegistPostReq.getDong() != null){
            Long dongId = Long.parseLong(userRegistPostReq.getDong());
            Dong dong = dongRepository.findDongById(dongId).get();
            user.setDong(dong.getName());
        }

        userRepository.save(user);
    }
}
