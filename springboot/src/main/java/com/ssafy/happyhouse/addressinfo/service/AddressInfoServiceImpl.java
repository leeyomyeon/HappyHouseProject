package com.ssafy.happyhouse.addressinfo.service;

import com.ssafy.happyhouse.addressinfo.mapper.AddressInfoMapper;
import com.ssafy.happyhouse.addressinfo.model.AddressInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressInfoServiceImpl implements AddressInfoService{

    @Autowired
    AddressInfoMapper mapper;

    @Override
    public List<String> cityList() {
        return mapper.cityList();
    }

    @Override
    public List<String> gugunList(String city) {
        return mapper.gugunList(city);
    }

    @Override
    public List<AddressInfoModel> dongList(String city, String gugun) {
        return mapper.dongList(city, gugun);
    }
}
