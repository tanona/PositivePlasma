package com.tanona.bill.positiveplasma;

import android.app.DialogFragment;
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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewCarbDataActivity extends AppCompatActivity
        implements ConfirmDelete.NoticeDialogListener{
    private SQLiteDatabase db;
    private GlucoseDatabaseHelper dbHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_carb_data);
        // call function to fillTable
        fillTable();
    }
    public void fillTable() {
        try {
            SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
            db = myDatabaseHelper.getWritableDatabase();

            cursor = db.rawQuery("select * from CARBS", null);
            TableLayout carbTable = (TableLayout) findViewById(R.id.carbTable);
            carbTable.removeAllViews();
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

                    // add Carbs
                    String carbStr = cursor.getString(cursor.getColumnIndex("CARBS"));
                    TextView newCarbs = (TextView) View.inflate(this, R.layout.textview_layout, null);
                    newCarbs.setGravity(Gravity.RIGHT);
                    newCarbs.setText(carbStr);
                    newRow.addView(newCarbs, 2);
                    carbTable.addView(newRow);
                    cursor.moveToNext();
                }
            }
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, " Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void deleteAll(View view) {
        // use the ConfirmDelete class to make sure...
        showConfirmDelete(); ;
    }

    public void showConfirmDelete(){
        DialogFragment dialog = new ConfirmDelete();
        dialog.show(getFragmentManager(), "ConfirmDelete");
        onDialogPositiveClick(dialog);

    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        SQLiteOpenHelper myDatabaseHelper = new GlucoseDatabaseHelper(this);
        db = myDatabaseHelper.getWritableDatabase();
        db.execSQL("delete from " + "CARBS");
        fillTable();
    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        // do nothing

    }

    public void home(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}

