package com.ssafy.happyhouse.noticeboard.service;

import com.ssafy.happyhouse.noticeboard.mapper.NoticeBoardMapper;
import com.ssafy.happyhouse.noticeboard.model.NoticeBoardModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeBoardServiceImpl implements NoticeBoardService{
    @Autowired
    public NoticeBoardMapper mapper;

    @Override
    public int writeBoard(NoticeBoardModel boardDto) throws Exception {
        return mapper.writeBoard(boardDto);
    }

    @Override
    public List<NoticeBoardModel> showList() throws Exception {
        return mapper.showList();
    }

    @Override
    public int deleteBoard(int boardNum) throws Exception {
        return mapper.deleteBoard(boardNum);
    }

    @Override
    public NoticeBoardModel selectBoard(int boardNum) throws Exception {
        return mapper.selectBoard(boardNum);
    }

    @Override
    public List<NoticeBoardModel> searchBoard(String searchOption, String value, int limit, int offset) throws Exception {
        return mapper.searchBoard(searchOption, value, limit, offset);
    }

    @Override
    public int updateBoard(NoticeBoardModel boardDto) throws Exception {
        return mapper.updateBoard(boardDto);
    }

    @Override
    public int getTotalCount() throws Exception {
        return mapper.getTotalCount();
    }

    @Override
    public List<NoticeBoardModel> selectBoardLimitOffset(int limit, int offset) throws Exception {
        return mapper.selectBoardLimitOffset(limit, offset);
    }
}
