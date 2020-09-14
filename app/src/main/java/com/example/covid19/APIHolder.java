package com.example.covid19;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIHolder {
    @GET("stats/history/")
    Call<ConfirmCasesDemoClass> getConfirmedCases();

    @GET("stats/testing/history")
    Call<SamplesDemoClass> getSamplesTested();

    @GET("contacts")
    Call<ContactsModelClass> getContacts();

    @GET("hospitals/beds")
    Call<HospitalModelClass> getHospitalDetails();

    @GET("hospitals/medical-colleges")
    Call<CollegeModelClass> getMedicalColleges();

    @GET("notifications")
    Call<NotificationModelClass> getNotifications();


}
