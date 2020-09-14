package com.example.covid19;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeceasedDetailsActivity extends DemoBase implements View.OnClickListener {
    LineChart chart;
    ArrayList<String> xaxislabels = new ArrayList<>();
    DateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<Date> dates;
    Spinner state_spinner, age_spinner, gender_spinner;
    ArrayList<PatientDetails> patientDetailsArrayList = new ArrayList<>();
    ArrayList<String> states_list = new ArrayList<>();
    int p = 0, kk = 0, ss = 0;
    Map<String, Integer> mapper = new HashMap<>();
    ArrayList<Integer> min_array = new ArrayList<>();
    ArrayList<Integer> max_array = new ArrayList<>();
    Button start_date_btn, end_date_button, go_button, download;
    String start_date, end_date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deceased_details);
        chart = findViewById(R.id.page_chart);
        state_spinner = findViewById(R.id.spinner_states);
        age_spinner = findViewById(R.id.spinner_age);
        gender_spinner = findViewById(R.id.spinner_gender);
        start_date_btn = findViewById(R.id.start_date);
        end_date_button = findViewById(R.id.end_date);
        go_button = findViewById(R.id.letsgo);
        download = findViewById(R.id.actionSave);
        start_date_btn.setOnClickListener(this);
        end_date_button.setOnClickListener(this);
        go_button.setOnClickListener(this);
        download.setOnClickListener(this);

        loader();
        settingStateSpinner();
        settingAgeSpinner();
        settingGenderSpinner();


//
        Calendar cal = Calendar.getInstance();
        end_date = cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
        start_date = "30/01/2020";
        dates = getDates(start_date, end_date);
        for (int l = 0; l < dates.size(); l++) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
            xaxislabels.add(dateFormat1.format(dates.get(l)));
        }


        getAttachmentChartData();


    }

    private void loader() {
        for (int i = 0; i < 8; i++) {
            min_array.add((i) * 10);
            max_array.add((i) * 10 + 9);
        }
        max_array.set(7, 100);
        InputStream is = getResources().openRawResource(R.raw.covid19india);
        Log.d("sriiiii", "I am here also");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
        try {
            reader.readLine();
            Map<String, Integer> m = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] properties = line.split(",");
//                Log.d("vvvvvvvv",""+properties.length);
                if (properties.length > 8) {
                    if (properties[8].equals("Deceased")) {
                        PatientDetails patientDetails = new PatientDetails(null, null, null, null, null, null);
                        if (properties[0] != null && !properties[0].equals(""))
                            patientDetails.setPatient_id(Integer.parseInt(properties[0]));
                        if (properties[3] != null && !properties[3].equals(""))
                            patientDetails.setAge(Integer.parseInt(properties[3]));
                        patientDetails.setGender(properties[4]);
                        patientDetails.setReported_on(properties[1]);
                        patientDetails.setState(properties[7]);
                        patientDetails.setStatus(properties[8]);
                        patientDetailsArrayList.add(patientDetails);
                        if (!m.containsKey(properties[7])) {
                            states_list.add(properties[7]);
                        }
                        if (properties[1] != null && mapper.containsKey(properties[1])) {
                            int s = mapper.get(properties[1]) + 1;
                            mapper.put(properties[1], s);
                        } else {
                            mapper.put(properties[1], 1);
                        }
                    }
                }


//                if(properties.length<9) Log.d("sri",properties[6]+"  aa "+ properties.length);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Date> getDates(String dateString1, String dateString2) {
        Log.d("cool", "i am here");
        ArrayList<Date> dates = new ArrayList<Date>();
        Date date1 = null, date2 = null;
        try {
            date1 = df1.parse(dateString1);
            Log.d("date1   ", "" + date1);
            date2 = df1.parse(dateString2);
            Log.d("date2   ", "" + date2);

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("error", "myeroor");
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private void setDatacharts(int count, ArrayList<Integer> no_of_deceased) {
        setingChartParameters();
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<Integer> no_of_users = new ArrayList<>(count);
        no_of_users.addAll(no_of_deceased);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setLabelRotationAngle(-45f);


        for (int i = 0; i < count; i++) {
            values.add(new Entry(i, no_of_users.get(i), null));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {

            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(1.5f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
//            set1.setFormLineWidth(1f);
//            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(0f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.color.red);
                set1.setFillColor(Color.WHITE);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
            //chart.setVisibleXRangeMaximum(15);
        }
    }

    private void setingChartParameters() {
        {
//            progressBar.setVisibility(View.GONE);
//            chart = findViewById(R.id.notieces_page_chart);
            chart.setVisibility(View.VISIBLE);
            chart.setBackgroundColor(Color.WHITE);
            chart.setExtraRightOffset(25);
//            chart.getDescription().setEnabled(true);
//            Description description = new Description();
//            // description.setText(UISetters.getFullMonthName());//commented for weekly reporting
//            description.setText("Week");
//            description.setTextSize(15f);
//            description.setPosition(0,5000);
//            chart.setDescription(description);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
            //  chart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
            chart.setDrawGridBackground(false);


            // enable scaling and dragging
            chart.setDragEnabled(true);
            chart.setScaleEnabled(true);
            MyMarkerView mv = new MyMarkerView(getApplicationContext(), R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart);
            chart.setMarker(mv);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(true);
            Legend l = chart.getLegend();
            chart.animateX(2500);
//       chart.setVisibleXRange(0,10);
            // draw legend entries as lines
            l.setForm(Legend.LegendForm.CIRCLE);
            l.setFormSize(0f);


            XAxis xAxis;
            {   // // X-Axis Style // //
                xAxis = chart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setAxisLineWidth(1);

                String[] labels = new String[xaxislabels.size()];
                for (int k = 0; k < xaxislabels.size(); k++) {
                    labels[k] = xaxislabels.get(k);
                }
                chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));


                // xAxis.setAxisMaximum(10);
                // vertical grid lines
//                xAxis.enableGridDashedLine(10f, 10f, 0f);
                xAxis.disableGridDashedLine();
                xAxis.setDrawGridLines(false);

            }

            YAxis yAxis;
            {   // // Y-Axis Style // //
                yAxis = chart.getAxisLeft();


                // disable dual axis (only use LEFT axis)
                chart.getAxisRight().setEnabled(false);

                // horizontal grid lines

//                yAxis.enableGridDashedLine(10f, 10f, 0f);
                //  yAxis.setAxisMaximum(100);
                // axis range
//                yAxis.setAxisMaximum(200f);
                yAxis.setAxisMinimum(0);

            }
        }
    }

    private void getAttachmentChartData() {
        final ArrayList<Integer> deceased_count = new ArrayList<>();
//        DataBaseHelper dataBaseHelper=new DataBaseHelper(getApplicationContext());
//         mapper=dataBaseHelper.getAllDeceasedPersonCountDate();
        Log.d("hahahah", mapper.toString());
        Log.d("srivvv", xaxislabels.toString());


//
        for (int i = 0; i < xaxislabels.size(); i++) {
            int p = 0;
            if (mapper.containsKey(xaxislabels.get(i))) {
                p = mapper.get(xaxislabels.get(i));
            }
//            String s = objects.get(i).get("all_attachment").toString();
            deceased_count.add(p);
        }

        setDatacharts(deceased_count.size(), deceased_count);


    }

    private void settingStateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeceasedDetailsActivity.this, android.R.layout.simple_spinner_item, states_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter,
                        R.layout.spinner_row_for_states,
                        DeceasedDetailsActivity.this));
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int k, long l) {

                if (kk != 0 && k != 0) {
                    mapper.clear();
                    String selected_state = states_list.get(k - 1);
                    Toast.makeText(getApplicationContext(), selected_state, Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < patientDetailsArrayList.size(); i++) {
                        PatientDetails patient = patientDetailsArrayList.get(i);
                        if (patient.getState() != null && patient.getState().equals(selected_state)) {
                            if (patient.getReported_on() != null && mapper.containsKey(patient.getReported_on())) {
                                int s = mapper.get(patient.getReported_on()) + 1;
                                mapper.put(patient.getReported_on(), s);
                            } else {
                                mapper.put(patient.getReported_on(), 1);
                            }

                        }
                    }
                    getAttachmentChartData();

//

                }
                if (kk == 0) {
                    kk++;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void settingAgeSpinner() {
        final ArrayList<String> age_list = new ArrayList<>();
        age_list.add("0 - 9");
        age_list.add("10 - 19");
        age_list.add("20 - 29");
        age_list.add("30 - 39");
        age_list.add("40 - 49");
        age_list.add("50 - 59");
        age_list.add("60 - 69");
        age_list.add("70 + ");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeceasedDetailsActivity.this, android.R.layout.simple_spinner_item, age_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        age_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter,
                        R.layout.spinner_row_for_age,
                        DeceasedDetailsActivity.this));
        age_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int k, long l) {
                if (ss != 0 && k != 0) {
                    String selected_state = age_list.get(k - 1);
                    Toast.makeText(getApplicationContext(), selected_state, Toast.LENGTH_SHORT).show();
//                    load(selected_state,type)
                    mapper.clear();

                    for (int i = 0; i < patientDetailsArrayList.size(); i++) {
                        PatientDetails patient = patientDetailsArrayList.get(i);
                        if (patient.getAge() != null && patient.getAge() <= max_array.get(k - 1) && patient.getAge() >= min_array.get(k - 1)) {
                            if (patient.getReported_on() != null && mapper.containsKey(patient.getReported_on())) {
                                int s = mapper.get(patient.getReported_on()) + 1;
                                mapper.put(patient.getReported_on(), s);
                            } else {
                                mapper.put(patient.getReported_on(), 1);
                            }

                        }
                    }

                    getAttachmentChartData();

                }
                if (ss == 0) {
                    ss++;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void settingGenderSpinner() {
        final ArrayList<String> gender_list = new ArrayList<>();
        gender_list.add("male");
        gender_list.add("female");
        gender_list.add("others");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeceasedDetailsActivity.this, android.R.layout.simple_spinner_item, gender_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter(
                new NothingSelectedSpinnerAdapter(adapter,
                        R.layout.spinner_row_for_gender,
                        DeceasedDetailsActivity.this));
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (p != 0 && i != 0) {
                    String selected_state = gender_list.get(i - 1);
                    Toast.makeText(getApplicationContext(), selected_state, Toast.LENGTH_SHORT).show();
                    final ArrayList<Integer> deceased_count = new ArrayList<>();
                    mapper.clear();
                    if (selected_state.equals("Male")) {
                        for (int k = 0; k < patientDetailsArrayList.size(); k++) {
                            if (patientDetailsArrayList.get(k).getGender() != null && patientDetailsArrayList.get(k).getGender().equals("male")) {
                                if (patientDetailsArrayList.get(k).getReported_on() != null && mapper.containsKey(patientDetailsArrayList.get(k).getReported_on())) {
                                    int s = mapper.get(patientDetailsArrayList.get(k).getReported_on()) + 1;
                                    mapper.put(patientDetailsArrayList.get(k).getReported_on(), s);
                                } else {
                                    mapper.put(patientDetailsArrayList.get(k).getReported_on(), 1);
                                }
                            }

                        }
                        getAttachmentChartData();
                    } else if (selected_state.equals("Female")) {
                        for (int k = 0; k < patientDetailsArrayList.size(); k++) {
                            if (patientDetailsArrayList.get(k).getGender() != null && patientDetailsArrayList.get(k).getGender().equals("female")) {
                                if (patientDetailsArrayList.get(k).getReported_on() != null && mapper.containsKey(patientDetailsArrayList.get(k).getReported_on())) {
                                    int s = mapper.get(patientDetailsArrayList.get(i).getReported_on()) + 1;
                                    mapper.put(patientDetailsArrayList.get(k).getReported_on(), s);
                                } else {
                                    mapper.put(patientDetailsArrayList.get(k).getReported_on(), 1);
                                }
                            }

                        }
                        getAttachmentChartData();

                    } else {
                        for (int k = 0; k < patientDetailsArrayList.size(); k++) {
                            if (patientDetailsArrayList.get(k).getGender() == null || patientDetailsArrayList.get(k).getGender().equals("")) {
                                if (patientDetailsArrayList.get(k).getReported_on() != null && mapper.containsKey(patientDetailsArrayList.get(k).getReported_on())) {
                                    int s = mapper.get(patientDetailsArrayList.get(k).getReported_on()) + 1;
                                    mapper.put(patientDetailsArrayList.get(k).getReported_on(), s);
                                } else {
                                    mapper.put(patientDetailsArrayList.get(k).getReported_on(), 1);
                                }
                            }


                        }
                        getAttachmentChartData();

                    }

//                    load(selected_state,type);

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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.letsgo:

                dates.clear();
                xaxislabels.clear();
                dates = getDates(start_date, end_date);
                for (int l = 0; l < dates.size(); l++) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                    xaxislabels.add(dateFormat1.format(dates.get(l)));
                }
                getAttachmentChartData();
                break;
            case R.id.end_date:
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(DeceasedDetailsActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                        mmonth += 1;
//                        isEndDateclicked = true;

                        end_date_button.setText(mdayOfMonth + "/" + mmonth + "/" + myear);
                        end_date = mdayOfMonth + "/" + mmonth + "/" + myear;
//                        Log.d("jjjjjjjj", edate);
//                        max_date_in_milli_second = datetolong(end_date);


                    }
                }, year, month, day);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.getDatePicker().setMinDate(datetolong(start_date));

                break;
            case R.id.start_date:
                Calendar cal = Calendar.getInstance();
                int day2 = cal.get(Calendar.DAY_OF_MONTH);
                int month2 = cal.get(Calendar.MONTH);
                final int year2 = cal.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(DeceasedDetailsActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int mdayOfMonth) {
                        mmonth += 1;
//                        isStartDateclicked = true;
                        start_date = mdayOfMonth + "/" + mmonth + "/" + myear;
                        start_date_btn.setText(mdayOfMonth + "/" + mmonth + "/" + myear);
//                        min_date_in_milli_second = datetolong(sdate);

                    }
                }, year2, month2, day2);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(datetolong(end_date));
                datePickerDialog.getDatePicker().setMinDate(datetolong("30/01/2020"));
                break;
            case R.id.actionSave:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;

        }


    }

    private long datetolong(String edate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        long t;
        try {
            Date mdate = sdf.parse(edate);
            t = mdate.getTime();
            return t;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "LineChartActivity1");
    }


}
