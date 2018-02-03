package com.hld.stockmanagerbusiness.bean;

public class HolderInfo {
    private long id;
    private long user_id;
    private long account_id;
    private String stock_code;
    private String stock_code_str;
    private String stock_name;
    private String cost_price;
    private String now_price;
    private long holder_num;
    private long usable_num;

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

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_code_str() {
        return stock_code_str;
    }

    public void setStock_code_str(String stock_code_str) {
        this.stock_code_str = stock_code_str;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getCost_price() {
        return cost_price;
    }

    public void setCost_price(String cost_price) {
        this.cost_price = cost_price;
    }

    public String getNow_price() {
        return now_price;
    }

    public void setNow_price(String now_price) {
        this.now_price = now_price;
    }

    public long getHolder_num() {
        return holder_num;
    }

    public void setHolder_num(long holder_num) {
        this.holder_num = holder_num;
    }

    public long getUsable_num() {
        return usable_num;
    }

    public void setUsable_num(long usable_num) {
        this.usable_num = usable_num;
    }
}
