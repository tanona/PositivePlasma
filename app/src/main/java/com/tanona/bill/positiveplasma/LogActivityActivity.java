package com.tanona.bill.positiveplasma;



import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/*
    Java class to support the logging of exercise activity. Reads the user entered exercise and duration
    and stores it in the ACTIVITY table of the glucose database with the current date and time.

 */

public class LogActivityActivity extends AppCompatActivity {
    double duration;
    String activity;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        getCurrentDate();
    }
    public void addActivity(View view) {

        // create an intent to return to the main activity.
        Intent intent = new Intent(this,MainActivity.class);

        GlucoseDatabaseHelper dbHelper = new GlucoseDatabaseHelper(this);

        db = dbHelper.getWritableDatabase();

        getCurrentDate();

        TextView dateTextView = (TextView) findViewById(R.id.current_date_view);
        String dateStr = (String) dateTextView.getText();

        TextView timeTextView = (TextView) findViewById(R.id.current_time_view);
        String timeStr = (String)timeTextView.getText();

        // get the activity value from the user entry box
        EditText activityText = (EditText)findViewById(R.id.activityEntry);
        activity=activityText.getText().toString(); //get the activity.
        EditText durationText = (EditText)findViewById(R.id.durationEntry);
        duration = Double.parseDouble(durationText.getText().toString());

        dbHelper.insertActivity(db, dateStr, timeStr, activity,duration);
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
