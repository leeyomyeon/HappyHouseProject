package com.ssafy.happyhouse.user.controller;

import com.ssafy.happyhouse.user.model.UserModel;
import com.ssafy.happyhouse.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/insert")
    public ResponseEntity<?> signInUser(@RequestBody UserModel userModel) throws Exception {
        int res = service.signInUser(userModel);

        if(res >= 1) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modifyUser(@RequestBody UserModel userModel) throws Exception {
        int res = service.modifyUser(userModel);

        if(res >= 1) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) throws Exception{
        int res = service.deleteUser(id);

        if(res >= 1) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchUser(@PathVariable("id") String id) throws Exception {
        // 한명만 찾음(해당유저가 있는지 없는지 검사)
        UserModel userModel = service.searchUser(id);

        if(userModel == null) {
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }

        return new ResponseEntity<>("found", HttpStatus.OK);
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<?> listUser(@PathVariable("id") String id) throws Exception {
        // 해당 ID가 포함된 모든 유저를 찾음
       List<UserModel> list = service.listUser(id);

        if (list.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
