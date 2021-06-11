package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPushListDAOList {
    @SerializedName("pu_id")
    @Expose
    private String puId;
    @SerializedName("pu_subject")
    @Expose
    private String puSubject;
    @SerializedName("wr_datetime")
    @Expose
    private String wrDatetime;

    public String getPuId() {
        return puId;
    }

    public void setPuId(String puId) {
        this.puId = puId;
    }

    public String getPuSubject() {
        return puSubject;
    }

    public void setPuSubject(String puSubject) {
        this.puSubject = puSubject;
    }

    public String getSendDatetime() {
        return wrDatetime;
    }

    public void setSendDatetime(String wrDatetime) {
        this.wrDatetime = wrDatetime;
    }
}
