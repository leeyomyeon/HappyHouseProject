package com.ssafy.happyhouse.newscroller.service;

import com.ssafy.happyhouse.newscroller.model.NewsCrollerModel;

import java.util.List;

public interface NewsCrollerService {
    public List<NewsCrollerModel> getNewsList(String city, String gugun) throws Exception;
}
