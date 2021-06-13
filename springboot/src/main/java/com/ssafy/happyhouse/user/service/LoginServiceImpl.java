package com.ssafy.happyhouse.user.service;

import com.ssafy.happyhouse.user.mapper.LoginMapper;
import com.ssafy.happyhouse.user.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginMapper mapper;

    public UserModel login(UserModel model) throws Exception {
        if(model.getId() == null || model.getPass() == null) {
            return null;
        }
        return mapper.login(model);
    }

    public UserModel userInfo(String id) throws Exception {
        return mapper.userInfo(id);
    }
}
