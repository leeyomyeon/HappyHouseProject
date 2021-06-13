package com.ssafy.happyhouse.homexmlparser.controller;

import com.ssafy.happyhouse.homexmlparser.model.HomeXmlParserModel;
import com.ssafy.happyhouse.homexmlparser.service.HomeXmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/hometrade")
public class HomeXmlParserController {
    @Autowired
    HomeXmlParserService service;

    @GetMapping("/{dong}/{date}")
    public ResponseEntity<?> getHomeTradeList(@PathVariable("dong") String dong, @PathVariable("date") String date) throws Exception {

        List<HomeXmlParserModel> list = service.getHomeTradeList(dong, date);

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
