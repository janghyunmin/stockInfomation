package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetLatelyCategoryDAOList {

    @SerializedName("ca_id2")
    @Expose
    private String caId2;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("code_name")
    @Expose
    private String codeName;

    public String getCaId2() {
        return caId2;
    }

    public void setCaId2(String caId2) {
        this.caId2 = caId2;
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
}
