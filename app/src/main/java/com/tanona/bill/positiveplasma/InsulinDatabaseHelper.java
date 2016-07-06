package com.tanona.bill.positiveplasma;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bill on 6/17/2016.
 */

public class InsulinDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "insulindb";
    private static int DB_VERSION = 1;

    InsulinDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE INSULIN("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "DATE TEXT,"
                + "TIME TEXT, "
                + "INSULIN DOUBLE);");
        ContentValues insulinValues = new ContentValues();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertInsulin(SQLiteDatabase db, String dateStr,String timeStr, double insulin) {
        ContentValues insulinValues = new ContentValues();
        insulinValues.put("DATE", dateStr);
        insulinValues.put("TIME", timeStr);
        insulinValues.put("INSULIN", insulin);
        db.insert("INSULIN", null, insulinValues);
    }

}
