package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSearchMainDAOList {
    @SerializedName("bo_table")
    @Expose
    private String boTable;
    @SerializedName("wr_id")
    @Expose
    private String wrId;
    @SerializedName("wr_subject")
    @Expose
    private String wrSubject;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("wr_hit")
    @Expose
    private String wrHit;
    @SerializedName("wr_reply")
    @Expose
    private String wrReply;
    @SerializedName("wr_like")
    @Expose
    private String wrLike;
    @SerializedName("like_check")
    @Expose
    private String likeCheck;
    @SerializedName("wr_file")
    @Expose
    private String wrFile;
    @SerializedName("detail_url")
    @Expose
    private String detailUrl;

    public String getBoTable() {
        return boTable;
    }

    public void setBoTable(String boTable) {
        this.boTable = boTable;
    }

    public String getWrId() {
        return wrId;
    }

    public void setWrId(String wrId) {
        this.wrId = wrId;
    }

    public String getWrSubject() {
        return wrSubject;
    }

    public void setWrSubject(String wrSubject) {
        this.wrSubject = wrSubject;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(String likeCheck) {
        this.likeCheck = likeCheck;
    }

    public String getWrFile() {
        return wrFile;
    }

    public void setWrFile(String wrFile) {
        this.wrFile = wrFile;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
