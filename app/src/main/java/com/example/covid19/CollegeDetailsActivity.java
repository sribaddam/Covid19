package com.example.covid19;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CollegeDetailsActivity extends AppCompatActivity {
    RecyclerView rc_contacts;
    APIHolder apiHolder;
    //    AdapterHospital adapterHospital;
    AdapterColleges adapterColleges;
    SearchView searchView;
    Spinner state_spinner, type_spinner;
    ArrayList<Map> college_list1;
    ArrayList<String> states_list = new ArrayList<>();
    ArrayList<String> type_list = new ArrayList<>();
    String state = "India", type = "All";
    int p, k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_details);
        rc_contacts = findViewById(R.id.rc_contacts);
        searchView = findViewById(R.id.search_view);
        RetroClient retroClient = new RetroClient();
        Retrofit retrofit = retroClient.getRetrofitInstance();
        state_spinner = findViewById(R.id.spinner_states);
        type_spinner = findViewById(R.id.spinner_type);
        states_list.add("India");
        type_list.add("All");
        p = 0;
        k = 0;


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
                adapterColleges.getFilter().filter(s);
                return true;
            }
        });
        getColleges();


    }

    private void settingStateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CollegeDetailsActivity.this, android.R.layout.simple_spinner_item, states_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter,
                        R.layout.spinner_row_for_states,
                        CollegeDetailsActivity.this));
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (p != 0 && i != 0) {
                    String selected_state = states_list.get(i - 1);
                    Toast.makeText(getApplicationContext(), selected_state, Toast.LENGTH_SHORT).show();
                    load(selected_state, type);

                }
                if (p == 0) {
                    p++;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void load(String selected_state, String selected_type) {
        state = selected_state;
        type = selected_type;
        if (selected_state.equals("India") && selected_type.equals("All")) {
            loadColleges(college_list1);
        } else if (selected_type.equals("All")) {
            ArrayList<Map> filtered_colleges = new ArrayList<>();
            for (int pp = 0; pp < college_list1.size(); pp++) {
                if (selected_state.equals(college_list1.get(pp).get("state").toString())) {
                    filtered_colleges.add(college_list1.get(pp));
                }
            }

            loadColleges(filtered_colleges);
        } else if (selected_state.equals("India")) {
            ArrayList<Map> filtered_colleges = new ArrayList<>();
            for (int pp = 0; pp < college_list1.size(); pp++) {
                if (college_list1.get(pp).get("ownership") != null && selected_type.equals(college_list1.get(pp).get("ownership").toString())) {
                    filtered_colleges.add(college_list1.get(pp));
                }
            }
            loadColleges(filtered_colleges);

        } else {
            ArrayList<Map> filtered_colleges = new ArrayList<>();
            for (int pp = 0; pp < college_list1.size(); pp++) {
                if (college_list1.get(pp).get("ownership") != null && selected_type.equals(college_list1.get(pp).get("ownership").toString())) {
                    filtered_colleges.add(college_list1.get(pp));
                }
            }

            ArrayList<Map> filtered_colleges2 = new ArrayList<>();
            for (int pp = 0; pp < filtered_colleges.size(); pp++) {
                if (selected_state.equals(filtered_colleges.get(pp).get("state").toString())) {
                    filtered_colleges2.add(filtered_colleges.get(pp));
                }
            }
            loadColleges(filtered_colleges2);
        }
    }

    private void settingTypeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CollegeDetailsActivity.this, android.R.layout.simple_spinner_item, type_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter,
                        R.layout.spinner_row_for_types,
                        CollegeDetailsActivity.this));
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (k != 0 && i != 0) {
                    String selected_type = type_list.get(i - 1);
                    Toast.makeText(getApplicationContext(), selected_type, Toast.LENGTH_SHORT).show();
                    load(state, selected_type);

                }
                if (k == 0) {
                    k++;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void loadColleges(ArrayList<Map> college_list) {

        adapterColleges = new AdapterColleges(college_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CollegeDetailsActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rc_contacts.setLayoutManager(linearLayoutManager);
        rc_contacts.setAdapter(adapterColleges);
    }

    private void getColleges() {
        Call<CollegeModelClass> call = apiHolder.getMedicalColleges();
        call.enqueue(new Callback<CollegeModelClass>() {
            @Override
            public void onResponse(Call<CollegeModelClass> call, Response<CollegeModelClass> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " UnSuccessful", Toast.LENGTH_SHORT).show();
                } else {

                    ArrayList<Map> college_list = response.body().getData().getMedicalColleges();
                    college_list1 = college_list;

                    load(state, type);
                    Map<String, Integer> m = new HashMap<>();
                    Map<String, Integer> map = new HashMap<>();
                    for (int i = 0; i < college_list1.size(); i++) {
                        m.put(college_list1.get(i).get("state").toString(), 1);
                        if (college_list1.get(i).get("ownership") != null)
                            map.put(college_list.get(i).get("ownership").toString(), 1);
                    }
                    for (Map.Entry<String, Integer> e1 : m.entrySet()) {
                        String s = e1.getKey();
                        states_list.add(s);
                    }
                    for (Map.Entry<String, Integer> e1 : map.entrySet()) {
                        String s = e1.getKey();
                        type_list.add(s);
                    }
                    settingStateSpinner();
                    settingTypeSpinner();


                }
            }

            @Override
            public void onFailure(Call<CollegeModelClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " Failure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
