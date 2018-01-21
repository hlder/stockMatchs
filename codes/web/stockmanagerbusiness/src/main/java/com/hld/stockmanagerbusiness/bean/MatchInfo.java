package com.hld.stockmanagerbusiness.bean;

public class MatchInfo {
    private long id;
    private String match_name;
    private String banners;
    private String buttons;
    private String match_note;
    private long max_people_num;
    private long now_people_num;
    private long creator_user_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getBanners() {
        return banners;
    }

    public void setBanners(String banners) {
        this.banners = banners;
    }

    public String getButtons() {
        return buttons;
    }

    public void setButtons(String buttons) {
        this.buttons = buttons;
    }

    public String getMatch_note() {
        return match_note;
    }

    public void setMatch_note(String match_note) {
        this.match_note = match_note;
    }

    public long getMax_people_num() {
        return max_people_num;
    }

    public void setMax_people_num(long max_people_num) {
        this.max_people_num = max_people_num;
    }

    public long getNow_people_num() {
        return now_people_num;
    }

    public void setNow_people_num(long now_people_num) {
        this.now_people_num = now_people_num;
    }

    public long getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(long creator_user_id) {
        this.creator_user_id = creator_user_id;
    }
}
