package com.example.covid19;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class StatisticsActivity extends AppCompatActivity {
    APIHolder apiHolder;
    LineChart chart1;
    ArrayList<String> xaxislabels = new ArrayList<>();
    ArrayList<String> date_array = new ArrayList<>();
    ArrayList<String> confirm_cases = new ArrayList<>();
    ArrayList<String> samples_tested = new ArrayList<>();
    Map<String, String> confirm_case_map = new HashMap<>();
    Map<String, String> samples_tested_map = new HashMap<>();
    ProgressBar progressBar;
    int p = 0;
    TextView tv1, tv2,tv3;
    LinearLayout l1,l2,l3,graphlayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);
        chart1 = findViewById(R.id.chart);
        tv1 = findViewById(R.id.date);
        tv2 = findViewById(R.id.cases);
        tv3=findViewById(R.id.tests);
        progressBar=findViewById(R.id.graph_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        l1=findViewById(R.id.lay1);
        l2=findViewById(R.id.lay2);
        l3=findViewById(R.id.lay3);
        chart1.setVisibility(View.GONE);
        graphlayout=findViewById(R.id.second_activity_graph);
//        graphlayout.setVisibility(View.GONE);

        RetroClient retroClient = new RetroClient();
        Retrofit retrofit = retroClient.getRetrofitInstance();

        apiHolder = retrofit.create(APIHolder.class);
        getingConfirmCases();
        chart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                final String x = chart1.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart1.getXAxis());
                String y=confirm_case_map.get(x);
                String z=samples_tested_map.get(x);
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.VISIBLE);
                l3.setVisibility(View.VISIBLE);
                tv1.setText(x);
                tv2.setText(y);
                tv3.setText(z);
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    private void getSamplesTested() {
        Call<SamplesDemoClass> call = apiHolder.getSamplesTested();
        call.enqueue(new Callback<SamplesDemoClass>() {
            @Override
            public void onResponse(Call<SamplesDemoClass> call, Response<SamplesDemoClass> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " Failure", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < response.body().data.size(); i++) {

                        Map data = response.body().data.get(i);
//                        xaxislabels.add(data.get("day").toString());

                        if (data.get("totalSamplesTested") != null)
                            samples_tested.add(data.get("totalSamplesTested").toString());
                        else samples_tested.add(samples_tested.get(i - 1));
                        samples_tested_map.put(data.get("day").toString(), samples_tested.get(i));
                    }
                    getAdmissionChartData();


                }

            }

            @Override
            public void onFailure(Call<SamplesDemoClass> call, Throwable t) {

            }
        });
    }

    private void getingConfirmCases() {
        Call<ConfirmCasesDemoClass> call = apiHolder.getConfirmedCases();
        call.enqueue(new Callback<ConfirmCasesDemoClass>() {
            @Override
            public void onResponse(Call<ConfirmCasesDemoClass> call, Response<ConfirmCasesDemoClass> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), " Failure", Toast.LENGTH_SHORT).show();

                } else {

                    for (int i = 0; i < response.body().data.size(); i++) {
                        ConfrimCasesData data = response.body().data.get(i);
                        confirm_cases.add(data.summary.get("total").toString());
                        String day = data.getDay();
                        date_array.add(day);
                        confirm_case_map.put(day, confirm_cases.get(i));
//                        Log.d("vvvvvv",data.summary.get("total").toString());


                    }
                    getSamplesTested();


                }

            }

            @Override
            public void onFailure(Call<ConfirmCasesDemoClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                Log.d("sri", "Failure");
            }
        });
    }

    private void getAdmissionChartData() {
        final ArrayList<Integer> signuped = new ArrayList<>();
        final ArrayList<Integer> whitelisted = new ArrayList<>();
        progressBar.setVisibility(View.GONE);
        chart1.setVisibility(View.VISIBLE);
//        graphlayout.setVisibility(View.VISIBLE);


        for (int i = 0; i < date_array.size(); i++) {

            if (samples_tested_map.containsKey(date_array.get(i))) {
                Double d = Double.parseDouble(confirm_case_map.get(date_array.get(i)));
                int p = (d.intValue());
                signuped.add(p);
                xaxislabels.add(date_array.get(i));

                Double l = Double.parseDouble(samples_tested_map.get(date_array.get(i)));
                int m = (l.intValue());
                whitelisted.add(m);
            }

        }

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> signup_values = new ArrayList<>();
        for (int i = 0; i < signuped.size(); i++) {
            signup_values.add(new Entry(i, signuped.get(i), null));
        }
        LineDataSet signup_line_data = new LineDataSet(signup_values, "Comfirmed Cases");
        signup_line_data.setLineWidth(1f);
        signup_line_data.setCircleRadius(2f);
        signup_line_data.setColor(Color.GREEN);
        signup_line_data.setCircleColor(Color.GREEN);
        signup_line_data.setValueTextSize(0f);

        dataSets.add(signup_line_data);

        ArrayList<Entry> whitelisted_values = new ArrayList<>();
        for (int i = 0; i < whitelisted.size(); i++) {
            whitelisted_values.add(new Entry(i, whitelisted.get(i), null));
        }

        LineDataSet whitelisted_line_data = new LineDataSet(whitelisted_values, "Samples Tested");
        whitelisted_line_data.setLineWidth(1f);
        whitelisted_line_data.setCircleRadius(2f);
        whitelisted_line_data.setColor(Color.RED);
        whitelisted_line_data.setCircleColor(Color.RED);
        whitelisted_line_data.setValueTextSize(0f);
        dataSets.add(whitelisted_line_data);
        {
            chart1 = findViewById(R.id.chart);
            chart1.setDrawGridBackground(false);
            chart1.getDescription().setEnabled(false);
            chart1.setDrawBorders(false);
            chart1.setBackgroundColor(Color.WHITE);
            chart1.setExtraRightOffset(25);


            chart1.getAxisLeft().setEnabled(true);
            chart1.getAxisRight().setDrawAxisLine(true);
            chart1.getAxisRight().setEnabled(false);
            chart1.getAxisRight().setDrawAxisLine(false);
            chart1.getAxisLeft().enableGridDashedLine(10f, 10f, 0f);
            chart1.getXAxis().setDrawAxisLine(true);
            chart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

            String[] labels = new String[xaxislabels.size()];
            for (int k = 0; k < xaxislabels.size(); k++) {
                labels[k] = xaxislabels.get(k);
            }
            chart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
            chart1.getXAxis().enableGridDashedLine(10f, 10f, 0f);


            // enable touch gestures
            chart1.setTouchEnabled(true);

            // enable scaling and dragging
            chart1.setDragEnabled(true);
            chart1.setScaleEnabled(true);
//            MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);
//
//            // Set the marker to the chart
//            mv.setChartView(chart1);
//            chart1.setMarker(mv);

            // if disabled, scaling can be done on x- and y-axis separately
            chart1.setPinchZoom(true);


            Legend l = chart1.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
        }

        setDatamultiple(whitelisted.size(), dataSets);

//                    TextView white_listed=findViewById(R.id.white_listed);
//                    white_listed.setText(addm.get("all_pending_user").toString());
//                    TextView signup=findViewById(R.id.signedup);
//                    signup.setText(addm.get("all_active_user").toString());
//                    TextView deletedusers=findViewById(R.id.all_deleted_users);
//                    deletedusers.setText(addm.get("all_deleted_user").toString());
    }

    private void setDatamultiple(int count, ArrayList<ILineDataSet> dataSets) {
        LineData data = new LineData(dataSets);
//        progressBar.setVisibility(View.GONE);
        chart1.getAxisLeft().setAxisMinimum(0);
        chart1.setVisibility(View.VISIBLE);
        chart1.animateX(2500);
        chart1.getXAxis().setGranularity(1f);
        chart1.getXAxis().setLabelRotationAngle(-45f);


        chart1.setData(data);

        chart1.invalidate();

        //chart.setVisibleXRangeMaximum(15);

    }


}
