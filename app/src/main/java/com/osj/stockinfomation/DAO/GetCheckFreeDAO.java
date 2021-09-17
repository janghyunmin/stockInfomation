package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCheckFreeDAO extends BaseDAO{
//    @SerializedName("check_yn")
//    @Expose
//    private String checkYn;
//
//    public String getCheckYn() {
//        return checkYn;
//    }
//
//    public void setCheckYn(String checkYn) {
//        this.checkYn = checkYn;
//    }

    @SerializedName("display_btn")
    @Expose
    private String display_btn;

    public String getDisplay_btn() {
        return display_btn;
    }

    public void setDisplay_btn(String display_btn) {
        this.display_btn = display_btn;
    }
}
