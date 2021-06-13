package com.ssafy.happyhouse.homexmlparser.service;

import com.ssafy.happyhouse.homexmlparser.model.HomeXmlParserModel;

import java.util.List;

public interface HomeXmlParserService {
    List<HomeXmlParserModel> getHomeTradeList(String dong, String date) throws Exception;
}
