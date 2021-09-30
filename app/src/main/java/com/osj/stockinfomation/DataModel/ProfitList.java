package com.osj.stockinfomation.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfitList {
    @SerializedName("idx")
    @Expose
    int idx;
    @SerializedName("stock_code")
    @Expose
    String stock_code;
    @SerializedName("stock_name")
    @Expose
    String stock_name;
    @SerializedName("m_status")
    @Expose
    String m_status;
    @SerializedName("buy_price")
    @Expose
    String buy_price;
    @SerializedName("per")
    @Expose
    String per;
    @SerializedName("date")
    @Expose
    String date;

    public ProfitList(int idx,String stock_code,String stock_name,String m_status,String buy_price,String per,String date){
        this.idx = idx;
        this.stock_code = stock_code;
        this.stock_name = stock_name;
        this.m_status = m_status;
        this.buy_price = buy_price;
        this.per = per;
        this.date = date;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getStock_code() {
        return stock_code;
    }

    public void setStock_code(String stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public String getM_status() {
        return m_status;
    }

    public void setM_status(String m_status) {
        this.m_status = m_status;
    }

    public String getBuy_price() {
        return buy_price;
    }

    public void setBuy_price(String buy_price) {
        this.buy_price = buy_price;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

