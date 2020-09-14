package com.example.covid19;

import java.util.ArrayList;
import java.util.Map;

public class NotificationDataSubModelClass {
    ArrayList<Map> notifications;

    public NotificationDataSubModelClass(ArrayList<Map> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Map> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Map> notifications) {
        this.notifications = notifications;
    }
}
