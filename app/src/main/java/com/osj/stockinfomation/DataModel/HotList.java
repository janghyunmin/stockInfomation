package com.osj.stockinfomation.DataModel;

public class HotList {
    private int idex;
    private String code; // 종목 코드
    private Integer trdPrc; // 현재가
    private String cmpprevddTpCd; // 전일대비 구분코드
    private Integer cmpprevddPrc; // 전일 대비 가격

    public HotList(int idex , String code , Integer trdPrc, String cmpprevddTpCd , Integer cmpprevddPrc){
        this.idex = idex;
        this.code = code;
        this.trdPrc = trdPrc;
        this.cmpprevddTpCd = cmpprevddTpCd;
        this.cmpprevddPrc = cmpprevddPrc;
    }

    public int getIdex() {
        return idex;
    }

    public void setIdex(int idex) {
        this.idex = idex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTrdPrc() {
        return trdPrc;
    }

    public void setTrdPrc(Integer trdPrc) {
        this.trdPrc = trdPrc;
    }

    public String getCmpprevddTpCd() {
        return cmpprevddTpCd;
    }

    public void setCmpprevddTpCd(String cmpprevddTpCd) {
        this.cmpprevddTpCd = cmpprevddTpCd;
    }

    public Integer getCmpprevddPrc() {
        return cmpprevddPrc;
    }

    public void setCmpprevddPrc(Integer cmpprevddPrc) {
        this.cmpprevddPrc = cmpprevddPrc;
    }
}
