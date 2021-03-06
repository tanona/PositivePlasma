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
    Java class to support the log insulin activity. Reads the user entered insulin measurement
    and stores it in the insulin database with the current date and time.

 */
public class LogInsulinActivity extends AppCompatActivity {

    double insulin;
    private SQLiteDatabase db;

        //  boolean isRunning = false;

    /*
      OnCreate: sets up the log_insulin activity
      gets the current date and time and updates
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_insulin);
        getCurrentDate();

    }
    public void addInsulin(View view) {
        // create an intent to return to the main activity.
        Intent intent = new Intent(this,MainActivity.class);

        //SQLiteOpenHelper myDatabaseHelper = new InsulinDatabaseHelper(this);
        GlucoseDatabaseHelper dbHelper = new GlucoseDatabaseHelper(this);

        db = dbHelper.getWritableDatabase();
        getCurrentDate();  // update the date and time
        TextView dateTextView = (TextView) findViewById(R.id.current_date_view);
        String dateStr = (String) dateTextView.getText();

        TextView timeTextView = (TextView) findViewById(R.id.current_time_view);
        String timeStr = (String)timeTextView.getText();

        // get the insulin value from the user entry box
        EditText insulinText = (EditText)findViewById(R.id.insulinEntry);
        insulin= Double.parseDouble(insulinText.getText().toString()); //insulinText.
        dbHelper.insertInsulin(db, dateStr, timeStr, insulin);
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
