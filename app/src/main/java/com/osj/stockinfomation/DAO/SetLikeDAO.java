package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetLikeDAO extends BaseDAO{
    @SerializedName("wr_like")
    @Expose
    private String wrLike;

    public String getWrLike() {
        return wrLike;
    }

    public void setWrLike(String wrLike) {
        this.wrLike = wrLike;
    }

}
