package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSearchMainDAO extends  BaseDAO{

    @SerializedName("list")
    @Expose
    private java.util.List<GetSearchMainDAOList> list = null;

    public java.util.List<GetSearchMainDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<GetSearchMainDAOList> list) {
        this.list = list;
    }

}
