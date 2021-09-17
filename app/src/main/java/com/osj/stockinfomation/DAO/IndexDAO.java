package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Result;

public class IndexDAO {

    @SerializedName("jsonrpc")
    @Expose
    private String jsonrpc;
    @SerializedName("result")
    @Expose
    private Result result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("isuSrtCd")
        @Expose
        private String isuSrtCd;
        @SerializedName("isuCnt")
        @Expose
        private Integer isuCnt;
        @SerializedName("trdPrc")
        @Expose
        private Double trdPrc;
        @SerializedName("cmpprevddTpCd")
        @Expose
        private String cmpprevddTpCd;
        @SerializedName("opnprc")
        @Expose
        private Double opnprc;
        @SerializedName("hgprc")
        @Expose
        private Double hgprc;
        @SerializedName("lwprc")
        @Expose
        private Double lwprc;
        @SerializedName("accTrdvol")
        @Expose
        private Integer accTrdvol;
        @SerializedName("accTrdval")
        @Expose
        private Integer accTrdval;
        @SerializedName("mktcap")
        @Expose
        private Long mktcap;
        @SerializedName("cmpprevddPrc")
        @Expose
        private Double cmpprevddPrc;
        @SerializedName("listShrs")
        @Expose
        private Integer listShrs;

        public String getIsuSrtCd() {
            return isuSrtCd;
        }

        public void setIsuSrtCd(String isuSrtCd) {
            this.isuSrtCd = isuSrtCd;
        }

        public Integer getIsuCnt() {
            return isuCnt;
        }

        public void setIsuCnt(Integer isuCnt) {
            this.isuCnt = isuCnt;
        }

        public Double getTrdPrc() {
            return trdPrc;
        }

        public void setTrdPrc(Double trdPrc) {
            this.trdPrc = trdPrc;
        }

        public String getCmpprevddTpCd() {
            return cmpprevddTpCd;
        }

        public void setCmpprevddTpCd(String cmpprevddTpCd) {
            this.cmpprevddTpCd = cmpprevddTpCd;
        }

        public Double getOpnprc() {
            return opnprc;
        }

        public void setOpnprc(Double opnprc) {
            this.opnprc = opnprc;
        }

        public Double getHgprc() {
            return hgprc;
        }

        public void setHgprc(Double hgprc) {
            this.hgprc = hgprc;
        }

        public Double getLwprc() {
            return lwprc;
        }

        public void setLwprc(Double lwprc) {
            this.lwprc = lwprc;
        }

        public Integer getAccTrdvol() {
            return accTrdvol;
        }

        public void setAccTrdvol(Integer accTrdvol) {
            this.accTrdvol = accTrdvol;
        }

        public Integer getAccTrdval() {
            return accTrdval;
        }

        public void setAccTrdval(Integer accTrdval) {
            this.accTrdval = accTrdval;
        }

        public Long getMktcap() {
            return mktcap;
        }

        public void setMktcap(Long mktcap) {
            this.mktcap = mktcap;
        }

        public Double getCmpprevddPrc() {
            return cmpprevddPrc;
        }

        public void setCmpprevddPrc(Double cmpprevddPrc) {
            this.cmpprevddPrc = cmpprevddPrc;
        }

        public Integer getListShrs() {
            return listShrs;
        }

        public void setListShrs(Integer listShrs) {
            this.listShrs = listShrs;
        }

    }

}
