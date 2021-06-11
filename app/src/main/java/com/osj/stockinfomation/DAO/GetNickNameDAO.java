package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetNickNameDAO extends BaseDAO{
    @SerializedName("mb_nick")
    @Expose
    private String mbNick;

    public String getMbNick() {
        return mbNick;
    }

    public void setMbNick(String mbNick) {
        this.mbNick = mbNick;
    }
}
