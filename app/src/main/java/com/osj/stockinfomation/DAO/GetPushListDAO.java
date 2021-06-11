package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPushListDAO extends BaseDAO{
    @SerializedName("list")
    @Expose
    private java.util.List<GetPushListDAOList> list = null;

    public java.util.List<GetPushListDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<GetPushListDAOList> list) {
        this.list = list;
    }
}
