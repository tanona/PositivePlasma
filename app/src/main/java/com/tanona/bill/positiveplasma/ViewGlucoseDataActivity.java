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

public class ViewGlucoseDataActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private GlucoseDatabaseHelper dbHelper;
    private Cursor cursor;
    private boolean deleteAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_glucose_data);

        fillTable();
    }

    public void fillTable() {
        try {
            SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
            db = myDatabaseHelper.getWritableDatabase();

            cursor = db.rawQuery("select * from GLUCOSE", null);
            TableLayout glucoseTable = (TableLayout) findViewById(R.id.glucoseTable);
            glucoseTable.removeAllViews();
            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    // add a new row for each reading
                    TableRow newRow = (TableRow) View.inflate(this, R.layout.tablerow_layout, null);
                    // use TableLayout.LayoutParams to set column width
                    newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT));
                  //  newRow.setWeightSum(4);
                    TableRow.LayoutParams lp;

                    // add date to row
                    String dateStr = cursor.getString(cursor.getColumnIndex("DATE"));

                    TextView newDate = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT, 1f);
                    lp.weight=1;

                    newDate.setText(dateStr);
                    newDate.setGravity(Gravity.LEFT);
                    newRow.addView(newDate, 0);
                    newDate.setLayoutParams(lp);

                    // add time name
                    String timeStr = cursor.getString(cursor.getColumnIndex("TIME"));
                    TextView newTimeName = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT, 1f);
                    lp.weight=1;
                    newTimeName.setGravity(Gravity.LEFT);
                 ///   newTimeName.setEms(12);
                    newTimeName.setText(timeStr);
                    newRow.addView(newTimeName, 1);
                    newTimeName.setLayoutParams(lp);

                    // add Glucose
                    String glucoseStr = cursor.getString(cursor.getColumnIndex("GLUCOSE"));
                    TextView newGlucose = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT, 2f);
                    lp.weight=2;
                    
                    newGlucose.setGravity(Gravity.RIGHT);
                    newGlucose.setText(glucoseStr);
                    newRow.addView(newGlucose, 2);
                    newGlucose.setLayoutParams(lp);

                    glucoseTable.addView(newRow);
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void deleteAll(View view) {
        //showMessage("delete confirm","Are you Sure?");
        showMessage("Are You Sure?","activities") ;
        if(deleteAll) {
            SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
            db = myDatabaseHelper.getWritableDatabase();
            db.execSQL("delete from " + "GLUCOSE");
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

