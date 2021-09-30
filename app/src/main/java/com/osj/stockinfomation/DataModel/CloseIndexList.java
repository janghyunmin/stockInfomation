package com.osj.stockinfomation.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CloseIndexList {
    @SerializedName("closeList")
    @Expose
    private List<Close> closeList = null;
    @SerializedName("result_code")
    @Expose
    private String resultCode;

    public List<Close> getCloseList() {
        return closeList;
    }

    public void setCloseList(List<Close> closeList) {
        this.closeList = closeList;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public class Close {
        @SerializedName("idx")
        @Expose
        private String idx;
        @SerializedName("trdPrc")
        @Expose
        private String trdPrc;
        @SerializedName("marketcode")
        @Expose
        private String marketcode;
        @SerializedName("issuecode")
        @Expose
        private String issuecode;
        @SerializedName("date_")
        @Expose
        private String date;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getTrdPrc() {
            return trdPrc;
        }

        public void setTrdPrc(String trdPrc) {
            this.trdPrc = trdPrc;
        }

        public String getMarketcode() {
            return marketcode;
        }

        public void setMarketcode(String marketcode) {
            this.marketcode = marketcode;
        }

        public String getIssuecode() {
            return issuecode;
        }

        public void setIssuecode(String issuecode) {
            this.issuecode = issuecode;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

    }
}
