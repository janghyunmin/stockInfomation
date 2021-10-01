package com.osj.stockinfomation.DataModel;

public class NewCategory02Model {
    private int icon;
    private String category02_name;
    private String ca_id2;
    private String code;

    public NewCategory02Model(int icon , String category02_name , String ca_id2 , String code){
        this.icon = icon;
        this.category02_name = category02_name;
        this.ca_id2 = ca_id2;
        this.code = code;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getCategory02_name() {
        return category02_name;
    }

    public void setCategory02_name(String category02_name) {
        this.category02_name = category02_name;
    }

    public String getCa_id2() {
        return ca_id2;
    }

    public void setCa_id2(String ca_id2) {
        this.ca_id2 = ca_id2;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
