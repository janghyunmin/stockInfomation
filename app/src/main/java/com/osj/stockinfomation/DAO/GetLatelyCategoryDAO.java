package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLatelyCategoryDAO extends BaseDAO{

    @SerializedName("list")
    @Expose
    private java.util.List<GetLatelyCategoryDAOList> list = null;

    public java.util.List<GetLatelyCategoryDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<GetLatelyCategoryDAOList> list) {
        this.list = list;
    }
}
