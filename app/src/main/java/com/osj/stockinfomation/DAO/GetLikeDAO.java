package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLikeDAO extends  BaseDAO{

    @SerializedName("list")
    @Expose
    private java.util.List<GetLikeDAOList> list = null;

    public java.util.List<GetLikeDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<GetLikeDAOList> list) {
        this.list = list;
    }
}
