package com.ssafy.happyhouse.addressinfo.mapper;

import com.ssafy.happyhouse.addressinfo.model.AddressInfoModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressInfoMapper {
    List<String> cityList();
    List<String> gugunList(String city);
    List<AddressInfoModel> dongList(String city, String gugun);
}
