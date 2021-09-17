package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserDAO {

    @SerializedName("userInfo")
    @Expose
    private List<UserInfo> userInfo = null;

    public List<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
    }
    public class UserInfo {

        @SerializedName("mb_id")
        @Expose
        private String mbId;
        @SerializedName("mb_name")
        @Expose
        private String mbName;
        @SerializedName("mb_hp")
        @Expose
        private String mbHp;
        @SerializedName("apply_status")
        @Expose
        private String applyStatus;
        @SerializedName("apply_datetime")
        @Expose
        private String applyDatetime;
        @SerializedName("push_cnt")
        @Expose
        private String pushCnt;
        @SerializedName("push_loop_cnt")
        @Expose
        private String pushLoopCnt;
        @SerializedName("display_btn")
        @Expose
        private String displayBtn;

        public String getMbId() {
            return mbId;
        }

        public void setMbId(String mbId) {
            this.mbId = mbId;
        }

        public String getMbName() {
            return mbName;
        }

        public void setMbName(String mbName) {
            this.mbName = mbName;
        }

        public String getMbHp() {
            return mbHp;
        }

        public void setMbHp(String mbHp) {
            this.mbHp = mbHp;
        }

        public String getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(String applyStatus) {
            this.applyStatus = applyStatus;
        }

        public String getApplyDatetime() {
            return applyDatetime;
        }

        public void setApplyDatetime(String applyDatetime) {
            this.applyDatetime = applyDatetime;
        }

        public String getPushCnt() {
            return pushCnt;
        }

        public void setPushCnt(String pushCnt) {
            this.pushCnt = pushCnt;
        }

        public String getPushLoopCnt() {
            return pushLoopCnt;
        }

        public void setPushLoopCnt(String pushLoopCnt) {
            this.pushLoopCnt = pushLoopCnt;
        }

        public String getDisplayBtn() {
            return displayBtn;
        }

        public void setDisplayBtn(String displayBtn) {
            this.displayBtn = displayBtn;
        }
    }
}
