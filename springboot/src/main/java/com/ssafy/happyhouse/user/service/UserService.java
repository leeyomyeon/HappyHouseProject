package com.ssafy.happyhouse.user.service;

import com.ssafy.happyhouse.user.model.UserModel;

import java.util.List;

public interface UserService {
    int signInUser(UserModel model) throws Exception;
    int modifyUser(UserModel model) throws Exception;
    int deleteUser(String id) throws Exception;
    UserModel searchUser(String id) throws Exception;
    List<UserModel> listUser(String id) throws Exception;
}
