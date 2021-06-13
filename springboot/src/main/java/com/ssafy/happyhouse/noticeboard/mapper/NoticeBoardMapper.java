package com.ssafy.happyhouse.noticeboard.mapper;

import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface NoticeBoardMapper {
    int writeBoard(NoticeBoardModel model) throws SQLException;
    List<NoticeBoardModel> showList() throws SQLException;
    int deleteBoard(int boardNum) throws SQLException;
    NoticeBoardModel selectBoard(int boardNum) throws SQLException;
    List<NoticeBoardModel> searchBoard(@Param("searchOption") String SearchOption,
                                       @Param("value") String value,
                                       @Param("limit") int limit,
                                       @Param("offset") int offset) throws SQLException;


    int updateBoard(NoticeBoardModel model) throws SQLException;

    int getTotalCount() throws SQLException;
    List<NoticeBoardModel> selectBoardLimitOffset(@Param("limit") int limit, @Param("offset") int offset) throws SQLException;
}
