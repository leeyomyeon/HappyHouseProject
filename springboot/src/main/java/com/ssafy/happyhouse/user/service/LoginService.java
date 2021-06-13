package com.ssafy.happyhouse.user.service;

import com.ssafy.happyhouse.user.model.UserModel;

public interface LoginService {
    UserModel login(UserModel model) throws Exception;
    UserModel userInfo(String id) throws Exception;
}
