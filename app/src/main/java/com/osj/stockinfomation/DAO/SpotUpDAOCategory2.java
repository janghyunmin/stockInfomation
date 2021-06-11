package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotUpDAOCategory2 extends BaseDAO{
    @SerializedName("list")
    @Expose
    private java.util.List<SpotUpDAOListCategory2> list = null;

    public java.util.List<SpotUpDAOListCategory2> getList() {
        return list;
    }

    public void setList(java.util.List<SpotUpDAOListCategory2> list) {
        this.list = list;
    }
}
