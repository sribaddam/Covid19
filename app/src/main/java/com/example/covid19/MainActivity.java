package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView contact, notification, hospitals, statistics, colleges, deceased;
        statistics = findViewById(R.id.statistics);
        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);

            }
        });
        contact = findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactDetailsActivity.class);
                startActivity(intent);

            }
        });
        hospitals = findViewById(R.id.dashboard);
        hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HospitalsDetailsActivity.class);
                startActivity(intent);

            }
        });
        colleges = findViewById(R.id.college);
        colleges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CollegeDetailsActivity.class);
                startActivity(intent);
            }
        });
        notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(intent);
            }
        });
        deceased = findViewById(R.id.deceased);
        deceased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeceasedDetailsActivity.class);
                startActivity(intent);
            }
        });

//        DataBaseHelper dataBaseHelper=new DataBaseHelper(getApplicationContext());
//        dataBaseHelper.load();


    }
}