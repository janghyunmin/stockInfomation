package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLikeDetailDAO extends BaseDAO{
    @SerializedName("wr_subject")
    @Expose
    private String wrSubject;
    @SerializedName("wr_content")
    @Expose
    private String wrContent;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("wr_name")
    @Expose
    private String wrName;
    @SerializedName("wr_hit")
    @Expose
    private String wrHit;
    @SerializedName("wr_reply")
    @Expose
    private String wrReply;
    @SerializedName("wr_like")
    @Expose
    private String wrLike;
    @SerializedName("wr_file")
    @Expose
    private String wrFile;
    @SerializedName("wr_datetime")
    @Expose
    private String wrDatetime;

    public String getWrSubject() {
        return wrSubject;
    }

    public void setWrSubject(String wrSubject) {
        this.wrSubject = wrSubject;
    }

    public String getWrContent() {
        return wrContent;
    }

    public void setWrContent(String wrContent) {
        this.wrContent = wrContent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getWrName() {
        return wrName;
    }

    public void setWrName(String wrName) {
        this.wrName = wrName;
    }

    public String getWrHit() {
        return wrHit;
    }

    public void setWrHit(String wrHit) {
        this.wrHit = wrHit;
    }

    public String getWrReply() {
        return wrReply;
    }

    public void setWrReply(String wrReply) {
        this.wrReply = wrReply;
    }

    public String getWrLike() {
        return wrLike;
    }

    public void setWrLike(String wrLike) {
        this.wrLike = wrLike;
    }

    public String getWrFile() {
        return wrFile;
    }

    public void setWrFile(String wrFile) {
        this.wrFile = wrFile;
    }

    public String getWrDatetime() {
        return wrDatetime;
    }

    public void setWrDatetime(String wrDatetime) {
        this.wrDatetime = wrDatetime;
    }
}
