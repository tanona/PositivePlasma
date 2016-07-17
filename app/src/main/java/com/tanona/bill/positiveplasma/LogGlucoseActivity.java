package com.tanona.bill.positiveplasma;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
    Java class to support the log glucose activity. Reads the user entered glucose measurement
    and stores it in the glucose database with the current date and time..

 */
public class LogGlucoseActivity extends AppCompatActivity  {

    Integer glucose;
    private SQLiteDatabase db;

    /*
      OnCreate: sets up the log_glucose activity
      gets the current date and time and updates
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_glucose);
        getCurrentDate();

    }
    public void addGlucose(View view) {
        Intent intent = new Intent(this,MainActivity.class);

        SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
        GlucoseDatabaseHelper dbHelper = new GlucoseDatabaseHelper(this);
        db = myDatabaseHelper.getWritableDatabase();

        getCurrentDate();

        TextView dateTextView = (TextView) findViewById(R.id.current_date_view);
        String dateStr = (String) dateTextView.getText();

        TextView timeTextView = (TextView) findViewById(R.id.current_time_view);
        String timeStr = (String)timeTextView.getText();

        // get the glucose value from the user entry box

        EditText glucoseText = (EditText)findViewById(R.id.glucoseEntry);
        glucose= Integer.parseInt(glucoseText.getText().toString()); //glucoseText.
        dbHelper.insertGlucose(db, dateStr, timeStr, glucose);
        startActivity(intent);
    }

    public void cancel(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mformat = new SimpleDateFormat(" yyyy-MM-dd ");
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");
        String strDate = mformat.format(calendar.getTime());
        String strTime = tformat.format(calendar.getTime());
        display(strDate, strTime);
    }

    private void display(String date,String time) {
        //  String date = timeFormat.format
        TextView textView = (TextView) findViewById(R.id.current_date_view);
        textView.setText(date);
        TextView ttextView =(TextView) findViewById(R.id.current_time_view);
        ttextView.setText(time);
    }


}