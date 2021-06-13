package com.ssafy.happyhouse.user.service;

import com.ssafy.happyhouse.user.mapper.UserMapper;
import com.ssafy.happyhouse.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    public UserMapper mapper;

    @Override
    public int signInUser(UserModel model) throws Exception {
        return mapper.signInUser(model);
    }
    @Override
    public int modifyUser(UserModel model) throws Exception {
        return mapper.modifyUser(model);
    }

    @Override
    public UserModel searchUser(String id) throws Exception {
        return mapper.searchUser(id);
    }

    @Override
    public int deleteUser(String id) throws Exception {
        return mapper.deleteUser(id);
    }

    @Override
    public List<UserModel> listUser(String id) throws Exception {
        return mapper.listUser(id);
    }
}
