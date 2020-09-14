package com.example.covid19;

import java.util.ArrayList;
import java.util.Map;

public class SamplesDemoClass {
    Boolean success;
    ArrayList<Map> data;
    String lastRefreshed, lastOriginUpdate;

    public SamplesDemoClass(Boolean success, ArrayList<Map> data, String lastRefreshed, String lastOriginUpdate) {
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

    public ArrayList<Map> getData() {
        return data;
    }

    public void setData(ArrayList<Map> data) {
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
