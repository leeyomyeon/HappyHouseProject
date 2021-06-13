package com.ssafy.happyhouse.addressinfo.controller;

import com.ssafy.happyhouse.addressinfo.model.AddressInfoModel;
import com.ssafy.happyhouse.addressinfo.service.AddressInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// localhost:8078/addressinfo/citylist
@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/addressinfo")
public class AddressInfoController {
    @Autowired
    private AddressInfoService service;

    @GetMapping("/citylist")
    public ResponseEntity<?> cityList() throws Exception {
        List<String> list = service.cityList();

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/gugunlist/{city}")
    public ResponseEntity<?> gugunList(@PathVariable("city") String city) throws Exception {
        List<String> list = service.gugunList(city);

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/dongList/{city}/{gugun}")
    public ResponseEntity<?> dongList(@PathVariable("city") String city, @PathVariable("gugun") String gugun) throws Exception {
        List<AddressInfoModel> list = service.dongList(city, gugun);

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
