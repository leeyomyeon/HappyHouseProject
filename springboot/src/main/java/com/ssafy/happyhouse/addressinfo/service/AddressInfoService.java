package com.ssafy.happyhouse.addressinfo.service;

import com.ssafy.happyhouse.addressinfo.model.AddressInfoModel;

import java.util.List;

public interface AddressInfoService {
    List<String> cityList();
    List<String> gugunList(String city);
    List<AddressInfoModel> dongList(String city, String gugun);
}
