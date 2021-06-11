package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotUpDAOCategory3 extends BaseDAO{
    @SerializedName("list")
    @Expose
    private java.util.List<SpotUpDAOListCategory3> list = null;

    public java.util.List<SpotUpDAOListCategory3> getList() {
        return list;
    }

    public void setList(java.util.List<SpotUpDAOListCategory3> list) {
        this.list = list;
    }
}
