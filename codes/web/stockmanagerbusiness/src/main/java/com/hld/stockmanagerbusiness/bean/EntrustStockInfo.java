package com.hld.stockmanagerbusiness.bean;

import java.util.Date;

public class EntrustStockInfo {
    private long id;
    private String stock_code;
    private String stock_code_str;
    private String stock_name;
    private String entrust_price;
    private long type;
    private String entrust_num;
    private long vol_num;
    private Date entrust_time;
    private long user_id;
    private long account_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEntrust_price() {
        return entrust_price;
    }

    public void setEntrust_price(String entrust_price) {
        this.entrust_price = entrust_price;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getEntrust_num() {
        return entrust_num;
    }

    public void setEntrust_num(String entrust_num) {
        this.entrust_num = entrust_num;
    }

    public long getVol_num() {
        return vol_num;
    }

    public void setVol_num(long vol_num) {
        this.vol_num = vol_num;
    }

    public Date getEntrust_time() {
        return entrust_time;
    }

    public void setEntrust_time(Date entrust_time) {
        this.entrust_time = entrust_time;
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
}
