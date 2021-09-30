package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfitDAO {
    @SerializedName("list")
    @Expose
    private List<ProfitDAOList> list = null;

    public List<ProfitDAOList> getList() {
        return list;
    }

    public void setList(List<ProfitDAOList> list) {
        this.list = list;
    }

    public class ProfitDAOList {
        @SerializedName("idx")
        @Expose
        private String idx;
        @SerializedName("stock_code")
        @Expose
        private String stockCode;
        @SerializedName("stock_name")
        @Expose
        private String stockName;
        @SerializedName("m_status")
        @Expose
        private String mStatus;
        @SerializedName("buy_price")
        @Expose
        private String buyPrice;
        @SerializedName("per")
        @Expose
        private String per;
        @SerializedName("date")
        @Expose
        private String date;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getStockCode() {
            return stockCode;
        }

        public void setStockCode(String stockCode) {
            this.stockCode = stockCode;
        }

        public String getStockName() {
            return stockName;
        }

        public void setStockName(String stockName) {
            this.stockName = stockName;
        }

        public String getmStatus() {
            return mStatus;
        }

        public void setmStatus(String mStatus) {
            this.mStatus = mStatus;
        }

        public String getBuyPrice() {
            return buyPrice;
        }

        public void setBuyPrice(String buyPrice) {
            this.buyPrice = buyPrice;
        }

        public String getPer() {
            return per;
        }

        public void setPer(String per) {
            this.per = per;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
