package com.ssafy.happyhouse.user.controller;

import com.ssafy.happyhouse.user.model.UserModel;
import com.ssafy.happyhouse.user.service.JwtServiceImpl;
import com.ssafy.happyhouse.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JwtServiceImpl jwtService;
    @Autowired
    private LoginService service;

    @PostMapping("/confirm")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserModel model) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            UserModel loginUser = service.login(model);
            if(loginUser != null) {
                String token = jwtService.create("userid", loginUser.getId(), "access-token");
                resultMap.put("access-token", token);
                resultMap.put("message", "success");
            } else {
                resultMap.put("message", "fail");
            }
            status = HttpStatus.ACCEPTED;
        } catch(Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(resultMap, status);
    }

    @GetMapping("/info/{userid}")
    public ResponseEntity<Map<String, Object>> getInfo(@PathVariable("userid")  String userid, HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        if (jwtService.isUsable(request.getHeader("access-token"))) {
            try {
//				로그인 사용자 정보.
                UserModel model = service.userInfo(userid);
                resultMap.put("userInfo", model);
                resultMap.put("message", "success");
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            resultMap.put("message", "fail");
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<>(resultMap, status);
    }
}
