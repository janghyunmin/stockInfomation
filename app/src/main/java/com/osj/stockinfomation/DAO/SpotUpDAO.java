package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotUpDAO extends BaseDAO{
    @SerializedName("list")
    @Expose
    private java.util.List<SpotUpDAOList> list = null;

    public java.util.List<SpotUpDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<SpotUpDAOList> list) {
        this.list = list;
    }
}
