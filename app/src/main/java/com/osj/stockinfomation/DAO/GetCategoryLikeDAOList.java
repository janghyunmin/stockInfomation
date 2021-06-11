package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCategoryLikeDAOList {

    @SerializedName("ca3_code")
    @Expose
    private String ca3Code;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("code_name")
    @Expose
    private String codeName;
    @SerializedName("change_")
    @Expose
    private String change;
    @SerializedName("displayed_price")
    @Expose
    private String displayedPrice;
    @SerializedName("signed_change_price")
    @Expose
    private String signedChangePrice;
    @SerializedName("per")
    @Expose
    private Double per;

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

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDisplayedPrice() {
        return displayedPrice;
    }

    public void setDisplayedPrice(String displayedPrice) {
        this.displayedPrice = displayedPrice;
    }

    public String getSignedChangePrice() {
        return signedChangePrice;
    }

    public void setSignedChangePrice(String signedChangePrice) {
        this.signedChangePrice = signedChangePrice;
    }

    public Double getPer() {
        return per;
    }

    public void setPer(Double per) {
        this.per = per;
    }

}
