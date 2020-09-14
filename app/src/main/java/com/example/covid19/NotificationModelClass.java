package com.example.covid19;

public class NotificationModelClass {
    Boolean success;
    NotificationDataSubModelClass data;
    String lastRefreshed, lastOriginUpdate;

    public NotificationModelClass(Boolean success, NotificationDataSubModelClass data, String lastRefreshed, String lastOriginUpdate) {
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

    public NotificationDataSubModelClass getData() {
        return data;
    }

    public void setData(NotificationDataSubModelClass data) {
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
