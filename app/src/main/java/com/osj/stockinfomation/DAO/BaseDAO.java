package com.osj.stockinfomation.DAO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseDAO {
    @SerializedName("result_code")
    @Expose
    private String resultCode;
    @SerializedName("message")
    @Expose
    private String message;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
