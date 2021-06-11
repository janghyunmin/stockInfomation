package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCategoryLikeDAO extends BaseDAO{
    @SerializedName("list")
    @Expose
    private java.util.List<GetCategoryLikeDAOList> list = null;

    public java.util.List<GetCategoryLikeDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<GetCategoryLikeDAOList> list) {
        this.list = list;
    }


}
