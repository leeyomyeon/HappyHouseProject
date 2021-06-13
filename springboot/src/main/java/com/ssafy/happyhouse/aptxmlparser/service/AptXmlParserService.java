package com.ssafy.happyhouse.aptxmlparser.service;

import com.ssafy.happyhouse.aptxmlparser.model.AptXmlParserModel;

import java.util.List;

public interface AptXmlParserService {
    List<AptXmlParserModel> getAptTradeList(String dong, String date) throws Exception;
}
