package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.osj.stockinfomation.DataModel.CloseIndexList;

import java.time.Clock;
import java.util.List;

public class CloseListDAO {
    @SerializedName("trdPrc")
    @Expose
    private String trdPrc;

    @SerializedName("date")
    @Expose
    private String date;

    public CloseListDAO(String trdPrc,String date){
        this.trdPrc = trdPrc;
        this.date = date;
    }

    public String getTrdPrc() {
        return trdPrc;
    }

    public void setTrdPrc(String trdPrc) {
        this.trdPrc = trdPrc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
