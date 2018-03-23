package com.hld.stockmanagerbusiness.bean;

public class AccountInfo {

    private long id;
    private long user_id;
    private String input_values;
    private String phone_num;
    private long match_id;
    private String profession;
    private String stu_class;
    private String stu_num;
    private String account_name;
    private String init_total_assets;
    private String total_assets;
    private String can_use_assets;
    private String leader;
    private long deal_count;
    private float total_income;
    private float total_income_rate;

    private long total_deal_success_num;
    private long total_deal_error_num;

    private float week_income;
    private float week_income_rate;
    private float month_income;
    private float month_income_rate;

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getCan_use_assets() {
        return can_use_assets;
    }

    public String getInit_total_assets() {
        return init_total_assets;
    }

    public void setInit_total_assets(String init_total_assets) {
        this.init_total_assets = init_total_assets;
    }

    public void setCan_use_assets(String can_use_assets) {
        this.can_use_assets = can_use_assets;
    }

    public long getDeal_count() {
        return deal_count;
    }

    public void setDeal_count(long deal_count) {
        this.deal_count = deal_count;
    }

    public float getTotal_income() {
        return total_income;
    }

    public void setTotal_income(float total_income) {
        this.total_income = total_income;
    }

    public float getTotal_income_rate() {
        return total_income_rate;
    }

    public void setTotal_income_rate(float total_income_rate) {
        this.total_income_rate = total_income_rate;
    }

    public long getTotal_deal_success_num() {
        return total_deal_success_num;
    }

    public void setTotal_deal_success_num(long total_deal_success_num) {
        this.total_deal_success_num = total_deal_success_num;
    }

    public long getTotal_deal_error_num() {
        return total_deal_error_num;
    }

    public void setTotal_deal_error_num(long total_deal_error_num) {
        this.total_deal_error_num = total_deal_error_num;
    }

    public float getWeek_income() {
        return week_income;
    }

    public void setWeek_income(float week_income) {
        this.week_income = week_income;
    }

    public float getWeek_income_rate() {
        return week_income_rate;
    }

    public void setWeek_income_rate(float week_income_rate) {
        this.week_income_rate = week_income_rate;
    }

    public float getMonth_income() {
        return month_income;
    }

    public void setMonth_income(float month_income) {
        this.month_income = month_income;
    }

    public float getMonth_income_rate() {
        return month_income_rate;
    }

    public void setMonth_income_rate(float month_income_rate) {
        this.month_income_rate = month_income_rate;
    }

    public String getTotal_assets() {
        return total_assets;
    }

    public void setTotal_assets(String total_assets) {
        this.total_assets = total_assets;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getInput_values() {
        return input_values;
    }

    public void setInput_values(String input_values) {
        this.input_values = input_values;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public long getMatch_id() {
        return match_id;
    }

    public void setMatch_id(long match_id) {
        this.match_id = match_id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getStu_class() {
        return stu_class;
    }

    public void setStu_class(String stu_class) {
        this.stu_class = stu_class;
    }

    public String getStu_num() {
        return stu_num;
    }

    public void setStu_num(String stu_num) {
        this.stu_num = stu_num;
    }
}
