package com.ssafy.happyhouse.searchCI.service;

import com.ssafy.happyhouse.searchCI.model.SearchCIModel;

import java.util.List;

public interface SearchCIService {
    List<SearchCIModel> searchCI(String shopName, String sidoName, String sigunguName);
}
