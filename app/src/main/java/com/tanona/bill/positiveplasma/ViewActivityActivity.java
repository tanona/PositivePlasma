package com.tanona.bill.positiveplasma;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivityActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private GlucoseDatabaseHelper dbHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        // call function to fillTable
        fillTable();
    }
    public void fillTable() {
        try {
            SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
            db = myDatabaseHelper.getWritableDatabase();

            cursor = db.rawQuery("select * from ACTIVITIES", null);
            TableLayout activityTable = (TableLayout) findViewById(R.id.activityTable);
            activityTable.removeAllViews();
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    // add a new row for each reading
                    TableRow newRow = (TableRow) View.inflate(this, R.layout.tablerow_layout, null);
                    // add date to row
                    String dateStr = cursor.getString(cursor.getColumnIndex("DATE"));

                    TextView newDate = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newDate.setText(dateStr);
                    newDate.setGravity(Gravity.LEFT);
                    newRow.addView(newDate, 0);

                    // add time name
                    String timeStr = cursor.getString(cursor.getColumnIndex("TIME"));
                    TextView newTimeName = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newTimeName.setGravity(Gravity.CENTER);
                    newTimeName.setEms(10);
                    newTimeName.setText(timeStr);
                    newRow.addView(newTimeName, 1);

                    // add Activity
                    String activityStr = cursor.getString(cursor.getColumnIndex("ACTIVITY"));
                    TextView newActivity = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newActivity.setGravity(Gravity.CENTER);
                    newActivity.setText(activityStr);
                    newRow.addView(newActivity, 2);
                  //  activityTable.addView(newRow);
                 //   cursor.moveToNext();

                    // add duration
                    String durationStr = cursor.getString(cursor.getColumnIndex("DURATION"));
                    TextView newDuration = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newDuration.setGravity(Gravity.RIGHT);
                    newDuration.setText(durationStr);
                    newRow.addView(newDuration, 3);
                    activityTable.addView(newRow);
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, " Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void deleteAll(View view) {
        SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
        db = myDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from " + "ACTIVITIES");
        fillTable();
    }
}

