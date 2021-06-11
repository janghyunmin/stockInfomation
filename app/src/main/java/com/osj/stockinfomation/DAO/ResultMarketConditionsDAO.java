package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultMarketConditionsDAO extends BaseDAO{

    @SerializedName("list")
    @Expose
    private java.util.List<ResultMarketConditionsDAOList> list = null;

    public java.util.List<ResultMarketConditionsDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<ResultMarketConditionsDAOList> list) {
        this.list = list;
    }

}