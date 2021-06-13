package com.ssafy.happyhouse.user.mapper;

import com.ssafy.happyhouse.user.model.UserModel;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface UserMapper {
    // 회원가입 회원탈퇴 비밀번호 변경 등등
    int signInUser(UserModel model) throws SQLException;
    int modifyUser(UserModel model) throws SQLException;
    int deleteUser(String id) throws SQLException;
    UserModel searchUser(String id) throws SQLException;
    List<UserModel> listUser(String id) throws SQLException;
}
