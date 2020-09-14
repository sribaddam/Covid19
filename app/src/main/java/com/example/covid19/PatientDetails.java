package com.example.covid19;

public class PatientDetails {
    Integer patient_id;
    String reported_on;
    Integer age;
    String gender;
    String state;
    String status;

    public PatientDetails(Integer patient_id, String reported_on, Integer age, String gender, String state, String status) {
        this.patient_id = patient_id;
        this.reported_on = reported_on;
        this.age = age;
        this.gender = gender;
        this.state = state;
        this.status = status;
    }

    public Integer getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(Integer patient_id) {
        this.patient_id = patient_id;
    }

    public String getReported_on() {
        return reported_on;
    }

    public void setReported_on(String reported_on) {
        this.reported_on = reported_on;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
