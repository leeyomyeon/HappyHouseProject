package com.ssafy.happyhouse.aptxmlparser.controller;

import com.ssafy.happyhouse.aptxmlparser.model.AptXmlParserModel;
import com.ssafy.happyhouse.aptxmlparser.service.AptXmlParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/apttrade")
public class AptXmlParserController {
    @Autowired
    AptXmlParserService service;

    @GetMapping("/{dong}/{date}")
    public ResponseEntity<?> getAptTradeList(@PathVariable("dong") String dong, @PathVariable("date") String date) throws Exception {

        List<AptXmlParserModel> list = service.getAptTradeList(dong, date);

        if(!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }
    }
}
