package com.ssafy.happyhouse.newscroller.controller;

import com.ssafy.happyhouse.newscroller.model.NewsCrollerModel;
import com.ssafy.happyhouse.newscroller.service.NewsCrollerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/newscroller")
public class NewsCrollerController {

    @Autowired
    private NewsCrollerService service;

    @GetMapping("/{city}/{gugun}")
    public ResponseEntity<?> getNewsList(@PathVariable("city") String city, @PathVariable("gugun") String gugun) throws Exception {
        List<NewsCrollerModel> list = service.getNewsList(city, gugun);

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
