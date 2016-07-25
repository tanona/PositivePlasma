package com.tanona.bill.positiveplasma;

/**
 * Created by Bill on 6/17/2016.
 * A helper class to manage database creation and version management.
 */

// imports

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GlucoseDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "glucosedb";
    private static int DB_VERSION = 3;   // version 2 adds insulin and carbs tables

    GlucoseDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE GLUCOSE("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DATE TEXT,"
                + "TIME TEXT, "
                + "GLUCOSE INT);");
        ContentValues glucoseValues = new ContentValues();


        db.execSQL("CREATE TABLE INSULIN("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DATE TEXT,"
                + "TIME TEXT, "
                + "INSULIN DOUBLE);");
        ContentValues insulinValues = new ContentValues();


        db.execSQL("CREATE TABLE CARBS("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DATE TEXT,"
                + "TIME TEXT, "
                + "CARBS DOUBLE);");
        ContentValues carbValues = new ContentValues();

        db.execSQL("CREATE TABLE ACTIVITIES("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DATE TEXT,"
                + "TIME TEXT, "
                + "ACTIVITY TEXT, "
                + "DURATION DOUBLE)");
        ContentValues activityValues = new ContentValues();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // version 2 of glucosedb adds a tables for insulin and carbs.

            db.execSQL("CREATE TABLE INSULIN("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT,"
                    + "TIME TEXT, "
                    + "INSULIN DOUBLE);");
            ContentValues insulinValues = new ContentValues();

            db.execSQL("CREATE TABLE CARBS("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT,"
                    + "TIME TEXT, "
                    + "CARBS DOUBLE);");
            ContentValues carbValues = new ContentValues();

            db.execSQL("CREATE TABLE ACTIVITIES("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT,"
                    + "TIME TEXT, "
                    + "ACTIVITY TEXT, "
                    + "DURATION DOUBLE)");
        }else if(oldVersion < 3){
            db.execSQL("CREATE TABLE ACTIVITIES("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "DATE TEXT,"
                    + "TIME TEXT, "
                    + "ACTIVITY TEXT, "
                    + "DURATION DOUBLE)");
        }
    }

    public void insertGlucose(SQLiteDatabase db, String dateStr, String timeStr, int glucose) {
        ContentValues glucoseValues = new ContentValues();
        glucoseValues.put("DATE", dateStr);
        glucoseValues.put("TIME", timeStr);
        glucoseValues.put("GLUCOSE", glucose);
        db.insert("GLUCOSE", null, glucoseValues);
    }

    public void insertInsulin(SQLiteDatabase db, String dateStr, String timeStr, double insulin) {
        ContentValues insulinValues = new ContentValues();
        insulinValues.put("DATE", dateStr);
        insulinValues.put("TIME", timeStr);
        insulinValues.put("INSULIN", insulin);
        db.insert("INSULIN", null, insulinValues);
    }

    public void insertCarbs(SQLiteDatabase db, String dateStr, String timeStr, int carbs) {
        ContentValues carbsValues = new ContentValues();
        carbsValues.put("DATE", dateStr);
        carbsValues.put("TIME", timeStr);
        carbsValues.put("CARBS", carbs);
        db.insert("CARBS", null, carbsValues);
    }
    public void insertActivity(SQLiteDatabase db, String dateStr, String timeStr, String activity, double duration) {
        ContentValues activityValues = new ContentValues();
        activityValues.put("DATE", dateStr);
        activityValues.put("TIME", timeStr);
        activityValues.put("ACTIVITY", activity);
        activityValues.put("DURATION", duration);
        db.insert("ACTIVITIES", null, activityValues);
    }
}
