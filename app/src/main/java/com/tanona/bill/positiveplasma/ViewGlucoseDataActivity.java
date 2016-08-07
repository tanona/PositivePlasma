package com.tanona.bill.positiveplasma;

import android.app.AlertDialog;
import android.app.DialogFragment;
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

public class ViewGlucoseDataActivity extends AppCompatActivity
                    implements  ConfirmDelete.NoticeDialogListener{
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
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT, 1f);
                    lp.weight=1;

                    newDate.setText(dateStr);
                    newDate.setGravity(Gravity.LEFT);
                    newRow.addView(newDate, 0);
                    newDate.setLayoutParams(lp);

                    // add time name
                    String timeStr = cursor.getString(cursor.getColumnIndex("TIME"));
                    TextView newTimeName = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT, 1f);
                    lp.weight=2;
                    newTimeName.setGravity(Gravity.LEFT);
                 ///   newTimeName.setEms(12);
                    newTimeName.setText(timeStr);
                    newRow.addView(newTimeName, 1);
                    newTimeName.setLayoutParams(lp);

                    // add Glucose
                    String glucoseStr = cursor.getString(cursor.getColumnIndex("GLUCOSE"));
                    TextView newGlucose = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.MATCH_PARENT, 2f);
                    lp.weight=4;
                    
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

    public void deleteGlucose(View view) {
        // use the ConfirmDelete class to make sure...
        showConfirmDelete(); ;
    }

    public void showConfirmDelete(){
        DialogFragment dialog = new ConfirmDelete();
        dialog.show(getFragmentManager(), "ConfirmDelete");

    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
        db = myDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from " + "GLUCOSE");
        fillTable();
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        // do nothing

    }


    // method for returning to the home screen
    public void home(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

