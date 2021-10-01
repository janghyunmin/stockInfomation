package com.osj.stockinfomation.DataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewCategory01Model {
    private String caId;
    private String code;
    private String codeName;

    public NewCategory01Model(String caId , String code , String codeName){
        this.caId = caId;
        this.code = code;
        this.codeName = codeName;
    }

    public String getCaId() {
        return caId;
    }

    public void setCaId(String caId) {
        this.caId = caId;
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
