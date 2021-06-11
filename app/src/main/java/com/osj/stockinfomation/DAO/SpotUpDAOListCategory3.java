package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotUpDAOListCategory3 {
    @SerializedName("ca_id3")
    @Expose
    private String caId3;
    @SerializedName("ca3_code")
    @Expose
    private String ca3Code;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("code_name")
    @Expose
    private String codeName;
    @SerializedName("acc_tade_volume")
    @Expose
    private Object accTadeVolume;
    @SerializedName("change_")
    @Expose
    private Object chage;
    @SerializedName("change_price")
    @Expose
    private String changePrice;
    @SerializedName("change_price_rate")
    @Expose
    private String changePriceRate;
    @SerializedName("displayed_price")
    @Expose
    private String displayedPrice;
    @SerializedName("like_yn")
    @Expose
    private String likeYn;
    @SerializedName("per")
    @Expose
    private Double per;


    public String getCaId3() {
        return caId3;
    }

    public void setCaId3(String caId3) {
        this.caId3 = caId3;
    }

    public String getCa3Code() {
        return ca3Code;
    }

    public void setCa3Code(String ca3Code) {
        this.ca3Code = ca3Code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Object getAccTadeVolume() {
        return accTadeVolume;
    }

    public void setAccTadeVolume(Object accTadeVolume) {
        this.accTadeVolume = accTadeVolume;
    }

    public Object getChage() {
        return chage;
    }

    public void setChage(Object chage) {
        this.chage = chage;
    }

    public String getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(String changePrice) {
        this.changePrice = changePrice;
    }

    public String getChangePriceRate() {
        return changePriceRate;
    }

    public void setChangePriceRate(String changePriceRate) {
        this.changePriceRate = changePriceRate;
    }

    public String getDisplayedPrice() {
        return displayedPrice;
    }

    public void setDisplayedPrice(String displayedPrice) {
        this.displayedPrice = displayedPrice;
    }

    public String getLikeYn() {
        return likeYn;
    }

    public void setLikeYn(String likeYn) {
        this.likeYn = likeYn;
    }

    public Double getPer() {
        return per;
    }

    public void setPer(Double Per) {
        this.per = per;
    }
}
