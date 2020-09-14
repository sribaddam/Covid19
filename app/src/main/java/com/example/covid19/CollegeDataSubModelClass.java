package com.example.covid19;

import java.util.ArrayList;
import java.util.Map;

public class CollegeDataSubModelClass {
    ArrayList<Map> medicalColleges;
    ArrayList<String> sources;

    public CollegeDataSubModelClass(ArrayList<Map> medicalColleges, ArrayList<String> sources) {
        this.medicalColleges = medicalColleges;
        this.sources = sources;
    }

    public ArrayList<Map> getMedicalColleges() {
        return medicalColleges;
    }

    public void setMedicalColleges(ArrayList<Map> medicalColleges) {
        this.medicalColleges = medicalColleges;
    }

    public ArrayList<String> getSources() {
        return sources;
    }

    public void setSources(ArrayList<String> sources) {
        this.sources = sources;
    }
}
