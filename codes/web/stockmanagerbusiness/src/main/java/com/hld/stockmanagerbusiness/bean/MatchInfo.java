package com.hld.stockmanagerbusiness.bean;

public class MatchInfo {
    private long id;
    private String match_name;
    private String banners;
    private String buttons;
    private String match_note;
    private String leader;
    private long max_people_num;
    private long now_people_num;
    private long creator_user_id;
    private int is_need_profession;
    private int is_need_stu_class;
    private int is_need_stu_num;
    private String apply_banner;
    private int apply_banner_width;
    private int apply_banner_height;
    private String init_total_assets;
    private String logo;
    private boolean isJoin;

    public boolean isJoin() {
        return isJoin;
    }

    public void setJoin(boolean join) {
        isJoin = join;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInit_total_assets() {
        return init_total_assets;
    }

    public void setInit_total_assets(String init_total_assets) {
        this.init_total_assets = init_total_assets;
    }

    public String getApply_banner() {
        return apply_banner;
    }

    public void setApply_banner(String apply_banner) {
        this.apply_banner = apply_banner;
    }

    public int getApply_banner_width() {
        return apply_banner_width;
    }

    public void setApply_banner_width(int apply_banner_width) {
        this.apply_banner_width = apply_banner_width;
    }

    public int getApply_banner_height() {
        return apply_banner_height;
    }

    public void setApply_banner_height(int apply_banner_height) {
        this.apply_banner_height = apply_banner_height;
    }

    public int getIs_need_profession() {
        return is_need_profession;
    }

    public void setIs_need_profession(int is_need_profession) {
        this.is_need_profession = is_need_profession;
    }

    public int getIs_need_stu_class() {
        return is_need_stu_class;
    }

    public void setIs_need_stu_class(int is_need_stu_class) {
        this.is_need_stu_class = is_need_stu_class;
    }

    public int getIs_need_stu_num() {
        return is_need_stu_num;
    }

    public void setIs_need_stu_num(int is_need_stu_num) {
        this.is_need_stu_num = is_need_stu_num;
    }

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
