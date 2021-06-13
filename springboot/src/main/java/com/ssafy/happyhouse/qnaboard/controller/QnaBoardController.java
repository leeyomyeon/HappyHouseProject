package com.ssafy.happyhouse.qnaboard.controller;

import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import com.ssafy.happyhouse.qnaboard.model.QnaBoardModel;
import com.ssafy.happyhouse.qnaboard.service.QnaBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RequestMapping("/qnaboard")
public class QnaBoardController {

    @Autowired
    private QnaBoardService service;

    @PostMapping("/write")
    public ResponseEntity<?> writeBoard(@RequestBody QnaBoardModel qnaBoardModel) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date time = new Date();

        qnaBoardModel.setRegDate(format.format(time));

        int res = service.writeBoard(qnaBoardModel);

        if (res >= 1) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> showList() throws Exception {
        List<QnaBoardModel> list = service.showList();

        if (list.isEmpty()) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{boardNum}")
    public ResponseEntity<?> deleteBoardUser(@PathVariable("boardNum") int boardNum) throws Exception {
        int res = service.deleteBoard(boardNum);

        if (res >= 1) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/select/{boardNum}")
    public ResponseEntity<?> selectBoard(@PathVariable("boardNum") int boardNum) throws Exception {
        QnaBoardModel qnaBoardModel = service.selectBoard(boardNum);

        if (qnaBoardModel == null) {
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(qnaBoardModel, HttpStatus.OK);
    }

    @GetMapping("/search/{searchOption}/{value}/{limit}/{offset}")
    public ResponseEntity<?> searchBoard(@PathVariable("searchOption") String searchOption,
                                         @PathVariable("value") String value,
                                         @PathVariable("limit") int limit,
                                         @PathVariable("offset") int offset) throws Exception {
        System.out.println(searchOption + ", " + value);

        List<QnaBoardModel> list = service.searchBoard(searchOption, value, limit, offset);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/modify")
    public ResponseEntity<?> updateBoard(@RequestBody QnaBoardModel qnaBoardModel) throws Exception {
        int res = service.updateBoard(qnaBoardModel);

        if (res >= 1) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pagelink/totalCnt")
    public ResponseEntity<?> getTotalCount() throws Exception {
        int cnt = service.getTotalCount();

        return new ResponseEntity<>(cnt, HttpStatus.OK);
    }

    @GetMapping("/pagelink/{limit}/{offset}")
    public ResponseEntity<?> selectBoardLimitOffset(@PathVariable("limit") int limit, @PathVariable("offset") int offset) throws Exception {
        List<QnaBoardModel> list = service.selectBoardLimitOffset(limit, offset);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
