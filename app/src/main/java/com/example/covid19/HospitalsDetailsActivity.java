package com.example.covid19;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HospitalsDetailsActivity extends AppCompatActivity {
    RecyclerView rc_contacts;
    APIHolder apiHolder;
    AdapterHospital adapterHospital;
    SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        rc_contacts = findViewById(R.id.rc_contacts);
        searchView = findViewById(R.id.search_view);
        RetroClient retroClient = new RetroClient();
        Retrofit retrofit = retroClient.getRetrofitInstance();


        apiHolder = retrofit.create(APIHolder.class);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterHospital.getFilter().filter(s);
                return true;
            }
        });


        Call<HospitalModelClass> call = apiHolder.getHospitalDetails();
        call.enqueue(new Callback<HospitalModelClass>() {
            @Override
            public void onResponse(Call<HospitalModelClass> call, Response<HospitalModelClass> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " Failure", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Map> hospital_list = response.body().getData().getRegional();
                    adapterHospital = new AdapterHospital(hospital_list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HospitalsDetailsActivity.this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rc_contacts.setLayoutManager(linearLayoutManager);
                    rc_contacts.setAdapter(adapterHospital);

                }
            }

            @Override
            public void onFailure(Call<HospitalModelClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
