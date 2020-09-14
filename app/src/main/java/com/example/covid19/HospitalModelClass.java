package com.example.covid19;

public class HospitalModelClass {
    Boolean success;
    HospitalDataSubModelClass data;
    String lastRefreshed;
    String lastOriginUpdate;

    public HospitalModelClass(Boolean success, HospitalDataSubModelClass data, String lastRefreshed, String lastOriginUpdate) {
        this.success = success;
        this.data = data;
        this.lastRefreshed = lastRefreshed;
        this.lastOriginUpdate = lastOriginUpdate;
    }

    public Boolean getSuccess() {
        return success;
    }

    public HospitalDataSubModelClass getData() {
        return data;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public String getLastOriginUpdate() {
        return lastOriginUpdate;
    }
}
