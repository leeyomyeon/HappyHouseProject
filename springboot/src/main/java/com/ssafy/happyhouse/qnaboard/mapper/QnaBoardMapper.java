package com.ssafy.happyhouse.qnaboard.mapper;

import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import com.ssafy.happyhouse.qnaboard.model.QnaBoardModel;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface QnaBoardMapper {
    int writeBoard(QnaBoardModel model) throws SQLException;
    List<QnaBoardModel> showList() throws SQLException;
    int deleteBoard(int boardNum) throws SQLException;
    QnaBoardModel selectBoard(int boardNum) throws SQLException;
    List<QnaBoardModel> searchBoard(@Param("searchOption") String SearchOption,
                                    @Param("value") String value,
                                    @Param("limit") int limit,
                                    @Param("offset") int offset) throws SQLException;
    int updateBoard(QnaBoardModel model) throws SQLException;

    int getTotalCount() throws SQLException;
    List<QnaBoardModel> selectBoardLimitOffset(@Param("limit") int limit, @Param("offset") int offset) throws SQLException;
}
