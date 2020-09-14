package com.example.covid19;

import com.google.gson.annotations.SerializedName;

public class ContactsModelClass {
    Boolean success;
    ContactDataSubModelClass data;
    String lastRefreshed, lastOriginUpdate;

    public ContactsModelClass(Boolean success, ContactDataSubModelClass data, String lastRefreshed, String lastOriginUpdate) {
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

    public ContactDataSubModelClass getData() {
        return data;
    }

    public void setData(ContactDataSubModelClass data) {
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
