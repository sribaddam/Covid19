package com.example.covid19;

import java.util.ArrayList;
import java.util.Map;

public class ConfrimCasesData {
    String day;
    Map summary;
    ArrayList<Map> regional;

    public ConfrimCasesData(String day, Map summary, ArrayList<Map> regional) {
        this.day = day;
        this.summary = summary;
        this.regional = regional;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Map getSummary() {
        return summary;
    }

    public void setSummary(Map summary) {
        this.summary = summary;
    }

    public ArrayList<Map> getRegional() {
        return regional;
    }

    public void setRegional(ArrayList<Map> regional) {
        this.regional = regional;
    }
}
