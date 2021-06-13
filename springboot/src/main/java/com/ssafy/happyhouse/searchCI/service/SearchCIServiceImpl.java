package com.ssafy.happyhouse.searchCI.service;

import com.ssafy.happyhouse.searchCI.mapper.SearchCIMapper;
import com.ssafy.happyhouse.searchCI.model.SearchCIModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchCIServiceImpl implements SearchCIService {

    @Autowired
    SearchCIMapper mapper;

    @Override
    public List<SearchCIModel> searchCI(String shopName, String sidoName, String sigunguName) {
        return mapper.searchCI(shopName, sidoName, sigunguName);
    }
}
