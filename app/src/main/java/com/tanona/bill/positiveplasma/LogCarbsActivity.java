package com.tanona.bill.positiveplasma;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tanona.bill.positiveplasma.DateGetter;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/*
    Java class to support the logging of carbohydrates consumed. Reads the user entered carbohydrates
    and stores it in the carbs table of the glucose database with the current date and time.

 */

public class LogCarbsActivity extends AppCompatActivity {
    int carbs;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_carbs);
      //  DateGetter.getCurrentDate();
      //  display(DateGetter.strDate, DateGetter.strTime);
        getCurrentDate();
    }
    public void addCarbs(View view) {

        // create an intent to return to the main activity.
        Intent intent = new Intent(this,MainActivity.class);

        //SQLiteOpenHelper myDatabaseHelper = new InsulinDatabaseHelper(this);
        GlucoseDatabaseHelper dbHelper = new GlucoseDatabaseHelper(this);
        //db = myDatabaseHelper.getWritableDatabase();
        db = dbHelper.getWritableDatabase();
      //  DateGetter.getCurrentDate();  // update the date and time
      //  display(DateGetter.strDate,DateGetter.strTime);
        getCurrentDate();

        TextView dateTextView = (TextView) findViewById(R.id.current_date_view);
        String dateStr = (String) dateTextView.getText();

        TextView timeTextView = (TextView) findViewById(R.id.current_time_view);
        String timeStr = (String)timeTextView.getText();

        // get the carbs value from the user entry box
        EditText carbsText = (EditText)findViewById(R.id.carbsEntry);
        carbs= Integer.parseInt(carbsText.getText().toString()); //get the carbs.
        dbHelper.insertCarbs(db, dateStr, timeStr, carbs);
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
/*
    private void display(String date,String time) {
        //  String date = timeFormat.format
        TextView textView = (TextView) findViewById(R.id.current_date_view);
        textView.setText(date);
        TextView ttextView =(TextView) findViewById(R.id.current_time_view);
        ttextView.setText(time);
    }
*/

}
