package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCategoryLikeFavDAO extends BaseDAO{
    @SerializedName("list")
    @Expose
    private java.util.List<GetCategoryLikeFavDAOList> list = null;

    public java.util.List<GetCategoryLikeFavDAOList> getList() {
        return list;
    }

    public void setList(java.util.List<GetCategoryLikeFavDAOList> list) {
        this.list = list;
    }
}
