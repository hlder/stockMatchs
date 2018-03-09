package com.hld.stockweb.bean;

public class UserInfoBean {
    private long id;
    private String user_name;
    private String pass_word;
    private String sms_num;
    private String match_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPass_word() {
        return pass_word;
    }

    public void setPass_word(String pass_word) {
        this.pass_word = pass_word;
    }

    public String getSms_num() {
        return sms_num;
    }

    public void setSms_num(String sms_num) {
        this.sms_num = sms_num;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "id=" + id +
                ", user_name='" + user_name + '\'' +
                ", pass_word='" + pass_word + '\'' +
                ", sms_num='" + sms_num + '\'' +
                ", match_id='" + match_id + '\'' +
                '}';
    }
}
