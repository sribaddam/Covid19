package com.example.covid19;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView rc_notices;
    APIHolder apiHolder;
    AdapterNotification adapterNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        rc_notices = findViewById(R.id.rc_notices);
        RetroClient retroClient = new RetroClient();
        Retrofit retrofit = retroClient.getRetrofitInstance();


        apiHolder = retrofit.create(APIHolder.class);
        Call<NotificationModelClass> call = apiHolder.getNotifications();
        call.enqueue(new Callback<NotificationModelClass>() {
            @Override
            public void onResponse(Call<NotificationModelClass> call, Response<NotificationModelClass> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " Failure", Toast.LENGTH_SHORT).show();

                } else {
                    ArrayList<Map> locationWiseContacts = response.body().getData().getNotifications();
                    adapterNotification = new AdapterNotification(locationWiseContacts);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rc_notices.setLayoutManager(linearLayoutManager);
                    rc_notices.setAdapter(adapterNotification);
                }
            }

            @Override
            public void onFailure(Call<NotificationModelClass> call, Throwable t) {

            }
        });
    }
}
