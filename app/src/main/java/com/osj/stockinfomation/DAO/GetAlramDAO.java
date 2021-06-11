package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAlramDAO extends BaseDAO{
    @SerializedName("al_all")
    @Expose
    private String alAll;
    @SerializedName("mb_1")
    @Expose
    private String mb1;
    @SerializedName("mb_2")
    @Expose
    private String mb2;
    @SerializedName("mb_3")
    @Expose
    private String mb3;

    public String getAlAll() {
        return alAll;
    }

    public void setAlAll(String alAll) {
        this.alAll = alAll;
    }

    public String getMb1() {
        return mb1;
    }

    public void setMb1(String mb1) {
        this.mb1 = mb1;
    }

    public String getMb2() {
        return mb2;
    }

    public void setMb2(String mb2) {
        this.mb2 = mb2;
    }

    public String getMb3() {
        return mb3;
    }

    public void setMb3(String mb3) {
        this.mb3 = mb3;
    }
}
