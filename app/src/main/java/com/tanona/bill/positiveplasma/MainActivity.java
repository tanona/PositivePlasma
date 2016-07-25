package com.tanona.bill.positiveplasma;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;


    private RadioGroup radioMapSelection;
    private RadioButton rBtnSelected;
    private Button viewMapButton;
    private int selected =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create the database

        try {
            SQLiteOpenHelper myDatabaseHelper = new com.tanona.bill.positiveplasma.GlucoseDatabaseHelper(this);
            db = myDatabaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

  //  @Override
    public void logGlucose(View view){
        Intent intent = new Intent(this,LogGlucoseActivity.class);
        startActivity(intent);

    }
    public void logInsulin(View view){
        Intent intent = new Intent(this,LogInsulinActivity.class);
        startActivity(intent);
    }

    public void logActivity(View view){
        Intent intent = new Intent(this,LogActivityActivity.class);
        startActivity(intent);
    }
    public void viewGlucseLogs(View view){
        Intent intent = new Intent(this,ViewGlucoseDataActivity.class);
        startActivity(intent);
    }

    public void viewInsulinLogs(View view){
        Intent intent = new Intent(this,ViewInsulinDataActivity.class);
        startActivity(intent);
    }

    public void viewCarbLogs(View view){
        Intent intent = new Intent(this,ViewCarbDataActivity.class);
        startActivity(intent);
    }
    public void viewActivityLogs(View view){
        Intent intent = new Intent(this,ViewActivityActivity.class);
        startActivity(intent);
    }

}
