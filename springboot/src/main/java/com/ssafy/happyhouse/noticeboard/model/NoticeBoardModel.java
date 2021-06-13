package com.ssafy.happyhouse.noticeboard.model;

public class NoticeBoardModel {
    int boardNum;
    String title;
    String writer;
    String contents;
    String regDate;

    @Override
    public String toString() {
        return "NoticeBoardModel[" +
                "boardNum=" + boardNum +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", contents='" + contents + '\'' +
                ", regDate='" + regDate + '\'' +
                ']';
    }

    public NoticeBoardModel() {

    }

    public NoticeBoardModel(int boardNum, String title, String writer, String contents, String regDate) {
        this.boardNum = boardNum;
        this.title = title;
        this.writer = writer;
        this.contents = contents;
        this.regDate = regDate;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
