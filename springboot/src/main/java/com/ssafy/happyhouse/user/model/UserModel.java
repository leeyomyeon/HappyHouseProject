package com.ssafy.happyhouse.user.model;

public class UserModel {
    private String id;
    private String pass;
    private String phone;
    private String mail;
    private String comment;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPass() {
        return pass;
    }
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserModel() {

    }

    public UserModel(String id, String pass, String phone, String mail, String comment) {
        this.id = id;
        this.pass = pass;
        this.phone = phone;
        this.mail = mail;
        this.comment = comment;
    }
}
