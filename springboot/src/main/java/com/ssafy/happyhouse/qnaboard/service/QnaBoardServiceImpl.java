package com.ssafy.happyhouse.qnaboard.service;

import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import com.ssafy.happyhouse.qnaboard.mapper.QnaBoardMapper;
import com.ssafy.happyhouse.qnaboard.model.QnaBoardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaBoardServiceImpl implements QnaBoardService {
    @Autowired
    public QnaBoardMapper mapper;

    @Override
    public int writeBoard(QnaBoardModel qnaBoardModel) throws Exception {
        return mapper.writeBoard(qnaBoardModel);
    }

    @Override
    public List<QnaBoardModel> showList() throws Exception {
        return mapper.showList();
    }

    @Override
    public int deleteBoard(int boardNum) throws Exception {
        return mapper.deleteBoard(boardNum);
    }

    @Override
    public QnaBoardModel selectBoard(int boardNum) throws Exception {
        return mapper.selectBoard(boardNum);
    }

    @Override
    public List<QnaBoardModel> searchBoard(String searchOption, String value, int limit, int offset) throws Exception {
        return mapper.searchBoard(searchOption, value, limit, offset);
    }

    @Override
    public int updateBoard(QnaBoardModel qnaBoardModel) throws Exception {
        return mapper.updateBoard(qnaBoardModel);
    }

    @Override
    public int getTotalCount() throws Exception {
        return mapper.getTotalCount();
    }

    @Override
    public List<QnaBoardModel> selectBoardLimitOffset(int limit, int offset) throws Exception {
        return mapper.selectBoardLimitOffset(limit, offset);
    }
}
