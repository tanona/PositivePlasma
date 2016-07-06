package com.tanona.bill.positiveplasma;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogInsulinActivity extends AppCompatActivity {

    double insulin;
    private SQLiteDatabase db;
    NumberPicker numberPicker;
    String nums[]= {"Select a value","0.1","0.2","0.3","0.4","0.5","0.5","0.7","0.8","0.9","1.0","1.1","1.2","1.3","1.4","1.5," +
            "1.6","1.7","1.8","1.9","2.0","2.1","2.2","2.3","2.4","2.5","2.6","2.7","2.8","2.9","3.0"};
    boolean isRunning = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_insulin);
        getCurrentDate();
        numberPicker = (NumberPicker)findViewById(R.id.InsNumberPicker);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(30);
        numberPicker.setValue(10);
    }
    public void addInsulin(View view) {
        Intent intent = new Intent(this,MainActivity.class);

        SQLiteOpenHelper myDatabaseHelper = new InsulinDatabaseHelper(this);
        InsulinDatabaseHelper dbHelper = new InsulinDatabaseHelper(this);
        db = myDatabaseHelper.getWritableDatabase();

        getCurrentDate();

        TextView dateTextView = (TextView) findViewById(R.id.current_date_view);
        String dateStr = (String) dateTextView.getText();

        TextView timeTextView = (TextView) findViewById(R.id.current_time_view);
        String timeStr = (String)timeTextView.getText();

        insulin=numberPicker.getValue();

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
