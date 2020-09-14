package com.example.covid19;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String PATIENT_ID = "patient_id";
    public static final String PATIENT_TABLE = "PATIENT_TABLE";
    public static final String REPORTED_ON = "reported_on";
    public static final String AGE_ESTIMATE = "age_estimate";
    public static final String GENDER = "gender";
    public static final String STATE = "state";
    public static final String STATUS = "status";
    public static final String ID = "ID";
    Context context;
    SQLiteDatabase db;


    public DataBaseHelper(@Nullable Context context) {

        super(context, "name", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + PATIENT_TABLE + "  (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PATIENT_ID + " INTEGER ," + REPORTED_ON + " TEXT," + AGE_ESTIMATE + " INTEGER," + GENDER + " TEXT," + STATE + " TEXT," + STATUS + " TEXT)";
        sqLiteDatabase.execSQL(query);
        InputStream is = context.getResources().openRawResource(R.raw.covid19india);
        Log.d("sriiiii", "I am here also");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line;
//        try {
//            reader.readLine();
//            while ((line = reader.readLine()) != null) {
//                String[] properties = line.split(",");
////                Log.d("vvvvvvvv",""+properties.length);
//                if(properties.length>8){
//                    ContentValues cv=new ContentValues(6);
//                    cv.put(PATIENT_ID,Integer.parseInt(properties[0]));
//                    cv.put(REPORTED_ON,properties[1]);
//                    cv.put(AGE_ESTIMATE,properties[3]);
//                    cv.put(GENDER,properties[4]);
//                    cv.put(STATE,properties[7]);
//                    cv.put(STATUS,properties[8]);
////                    Log.d("hiiiii","I am here");
//                    long insert = sqLiteDatabase.insert(PATIENT_TABLE, null, cv);
////                    if(insert==-1){
////                        Toast.makeText(context, " Failure", Toast.LENGTH_SHORT).show();
////                    }
////                    else{
////                        Toast.makeText(context, " Success", Toast.LENGTH_SHORT).show();
////                    }
//                }
//
//
////                if(properties.length<9) Log.d("sri",properties[6]+"  aa "+ properties.length);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        sqLiteDatabase.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void load() {
        Log.d("vvvvvvvvvv", "I am here ");
        SQLiteDatabase database = this.getReadableDatabase();
        String querry = "SELECT * FROM " + PATIENT_TABLE;
        Cursor cursor = database.rawQuery(querry, null);
        Log.d("huhu", cursor.toString());

    }

    public Map<String, Integer> getAllDeceasedPersonCountDate() {
        db = this.getWritableDatabase();
        String A = "DECEASED";
//        String query="SELECT COUNT("+PATIENT_ID+"),"+REPORTED_ON+","+STATUS+" FROM "+PATIENT_TABLE+" WHERE "+STATUS+"=DECEASED GROUP BY "+REPORTED_ON;
        String query = "SELECT COUNT(REPORTED_ON),REPORTED_ON FROM PATIENT_TABLE WHERE STATUS=\"Deceased\" GROUP BY REPORTED_ON";
        Cursor cursor = db.rawQuery(query, null);
        try {

            // If moveToFirst() returns false then cursor is empty
            if (!cursor.moveToFirst()) {
                return new HashMap<>();
            }
            Map<String, Integer> products = new HashMap<>();


            do {
                final int count = cursor.getColumnIndex("COUNT(REPORTED_ON)");
                final int date = cursor.getColumnIndex(REPORTED_ON);

                // Read the values of a row in the table using the indexes acquired above
                final Integer count_i = cursor.getInt(count);
                final String date_s = cursor.getString(date);


                products.put(date_s, count_i);


            } while (cursor.moveToNext());

            return products;

        } finally {
            // Don't forget to close the Cursor once you are done to avoid memory leaks.
            // Using a try/finally like in this example is usually the best way to handle this
            cursor.close();

            // close the database
            db.close();
        }


    }


}
