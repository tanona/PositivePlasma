package com.tanona.bill.positiveplasma;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    private boolean deleteAll = false;

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
                    String timeStr = cursor.getString(cursor.getColumnIndex("TIME"));

                    TextView newDate = (TextView) View.inflate(this, R.layout.textview_layout, null);
                   // newDate.setText(dateStr);
                    newDate.setText(dateStr+" / "+ timeStr);
                    newDate.setGravity(Gravity.START);
                    newRow.addView(newDate, 0);

                    // add time name
                    /*
                    String timeStr = cursor.getString(cursor.getColumnIndex("TIME"));
                    TextView newTimeName = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newTimeName.setGravity(Gravity.START);
                    newTimeName.setEms(10);
                    newTimeName.setText(timeStr);
                    newRow.addView(newTimeName, 1);
*/
                    // add Activity
                    String activityStr = cursor.getString(cursor.getColumnIndex("ACTIVITY"));
                    TextView newActivity = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newActivity.setGravity(Gravity.CENTER);
                    newActivity.setText(activityStr);
                    newRow.addView(newActivity, 1);
                  //  activityTable.addView(newRow);
                 //   cursor.moveToNext();

                    // add duration
                    String durationStr = cursor.getString(cursor.getColumnIndex("DURATION"));
                    TextView newDuration = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    //newDuration.setPadding(5,0,0,0);
                    newDuration.setGravity(Gravity.END);
                    newDuration.setText(durationStr);
                    newRow.addView(newDuration, 2);
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
        //showMessage("delete confirm","Are you Sure?");
        showMessage("Are You Sure?","activities") ;
        if(deleteAll)
        {
            SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
            db = myDatabaseHelper.getWritableDatabase();
            db.execSQL("delete from " + "ACTIVITIES");
            fillTable();
        }
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                    deleteAll=true;
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    deleteAll=false;
                    break;
            }
        }
    };
    public void showMessage(String title, String message){
        // use to display warning and information messages
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete all of the data?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
    public void home(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

