package com.ssafy.happyhouse.qnaboard.service;

import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import com.ssafy.happyhouse.qnaboard.model.QnaBoardModel;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnaBoardService {
    int writeBoard(QnaBoardModel qnaBoardModel) throws Exception;

    List<QnaBoardModel> showList() throws Exception;

    int deleteBoard(int boardNum) throws Exception;

    QnaBoardModel selectBoard(int boardNum) throws Exception;

    List<QnaBoardModel> searchBoard(String searchOption, String value, int limit, int offset) throws Exception;

    int updateBoard(QnaBoardModel qnaBoardModel) throws Exception;

    int getTotalCount() throws Exception;

    List<QnaBoardModel> selectBoardLimitOffset(@Param("limit") int limit, @Param("offset") int offset) throws Exception;
}
