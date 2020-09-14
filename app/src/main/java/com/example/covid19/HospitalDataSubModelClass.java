package com.example.covid19;

import java.util.ArrayList;
import java.util.Map;

public class HospitalDataSubModelClass {
    Map summary;
    ArrayList<Map> regional, sources;

    public HospitalDataSubModelClass(Map summary, ArrayList<Map> regional, ArrayList<Map> sources) {
        this.summary = summary;
        this.regional = regional;
        this.sources = sources;
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

    public ArrayList<Map> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Map> sources) {
        this.sources = sources;
    }
}
