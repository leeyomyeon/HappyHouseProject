package com.ssafy.happyhouse.noticeboard.service;

import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoticeBoardService {
    int writeBoard(NoticeBoardModel noticeBoardModel) throws Exception;

    List<NoticeBoardModel> showList() throws Exception;

    int deleteBoard(int boardNum) throws Exception;

    //    int deleteBoard(int boardNum, String writer) throws Exception;
    NoticeBoardModel selectBoard(int boardNum) throws Exception;

    List<NoticeBoardModel> searchBoard(String searchOption, String value, int limit, int offset) throws Exception;

    int updateBoard(NoticeBoardModel noticeBoardModel) throws Exception;

    public int getTotalCount() throws Exception;

    List<NoticeBoardModel> selectBoardLimitOffset(@Param("limit") int limit, @Param("offset") int offset) throws Exception;
}
