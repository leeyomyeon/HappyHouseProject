package com.ssafy.happyhouse.user.mapper;

import com.ssafy.happyhouse.user.model.UserModel;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper {
    // 로그인, 로그아웃
    UserModel login(UserModel model) throws Exception;
    UserModel userInfo(String id) throws Exception;
}
