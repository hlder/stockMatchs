package com.hld.stockserver.bean;

public class StockBean {
    private long id;
    private int stock_status;
    private String stock_name;
    private String stock_code;
    private String stock_code_str;
    private String now_price;
    private String last_price;

    public StockBean(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStock_status() {
        return stock_status;
    }

    public void setStock_status(int stock_status) {
        this.stock_status = stock_status;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
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

    public String getNow_price() {
        return now_price;
    }

    public void setNow_price(String now_price) {
        this.now_price = now_price;
    }

    public String getLast_price() {
        return last_price;
    }

    public void setLast_price(String last_price) {
        this.last_price = last_price;
    }
}
