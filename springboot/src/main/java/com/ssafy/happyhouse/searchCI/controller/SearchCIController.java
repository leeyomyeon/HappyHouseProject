package com.ssafy.happyhouse.searchCI.controller;

import com.ssafy.happyhouse.newscroller.model.NewsCrollerModel;
import com.ssafy.happyhouse.searchCI.model.SearchCIModel;
import com.ssafy.happyhouse.searchCI.service.SearchCIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/searchci")
public class SearchCIController {

    @Autowired
    SearchCIService service;

    @GetMapping("/{shopName}/{sidoName}/{sigunguName}")
    public ResponseEntity<?> searchCI(@PathVariable("shopName") String shopName,
                                          @PathVariable("sidoName") String sidoName,
                                          @PathVariable("sigunguName") String sigunguName) {

        List<SearchCIModel> list = service.searchCI(shopName, sidoName, sigunguName);

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
