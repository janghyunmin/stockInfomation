package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPushDetailDAO extends BaseDAO{
    @SerializedName("pu_subject")
    @Expose
    private String puSubject;
    @SerializedName("pu_content")
    @Expose
    private String puContent;
    @SerializedName("wr_name")
    @Expose
    private String wrName;
    @SerializedName("send_datetime")
    @Expose
    private String sendDatetime;

    public String getPuSubject() {
        return puSubject;
    }

    public void setPuSubject(String puSubject) {
        this.puSubject = puSubject;
    }

    public String getPuContent() {
        return puContent;
    }

    public void setPuContent(String puContent) {
        this.puContent = puContent;
    }

    public String getWrName() {
        return wrName;
    }

    public void setWrName(String wrName) {
        this.wrName = wrName;
    }

    public String getSendDatetime() {
        return sendDatetime;
    }

    public void setSendDatetime(String sendDatetime) {
        this.sendDatetime = sendDatetime;
    }

}
