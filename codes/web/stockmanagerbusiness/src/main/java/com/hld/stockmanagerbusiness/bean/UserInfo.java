package com.hld.stockmanagerbusiness.bean;

public class UserInfo {
    private long id;
    private String nicke_name;
    private String head_url;
    private String wx_union_id;
    private String wx_open_id;
    private long def_account_id;
    private String sex;
    private String province;
    private String city;
    private String privilege;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNicke_name() {
        return nicke_name;
    }

    public void setNicke_name(String nicke_name) {
        this.nicke_name = nicke_name;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getWx_union_id() {
        return wx_union_id;
    }

    public void setWx_union_id(String wx_union_id) {
        this.wx_union_id = wx_union_id;
    }

    public String getWx_open_id() {
        return wx_open_id;
    }

    public void setWx_open_id(String wx_open_id) {
        this.wx_open_id = wx_open_id;
    }

    public long getDef_account_id() {
        return def_account_id;
    }

    public void setDef_account_id(long def_account_id) {
        this.def_account_id = def_account_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
