package com.example.covid19;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ConfirmCasesDemoClass {
    Boolean success;
    @SerializedName("data")
    ArrayList<ConfrimCasesData> data;
    String lastRefreshed, lastOriginUpdate;

    public ConfirmCasesDemoClass(Boolean success, ArrayList<ConfrimCasesData> data, String lastRefreshed, String lastOriginUpdate) {
        this.success = success;
        this.data = data;
        this.lastRefreshed = lastRefreshed;
        this.lastOriginUpdate = lastOriginUpdate;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<ConfrimCasesData> getData() {
        return data;
    }

    public void setData(ArrayList<ConfrimCasesData> data) {
        this.data = data;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public void setLastRefreshed(String lastRefreshed) {
        this.lastRefreshed = lastRefreshed;
    }

    public String getLastOriginUpdate() {
        return lastOriginUpdate;
    }

    public void setLastOriginUpdate(String lastOriginUpdate) {
        this.lastOriginUpdate = lastOriginUpdate;
    }
}
