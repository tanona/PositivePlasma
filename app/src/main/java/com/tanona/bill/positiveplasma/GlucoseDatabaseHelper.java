package com.tanona.bill.positiveplasma;

/**
 * Created by Bill on 6/17/2016.
 * A helper class to manage database creation and version management.
 *
 */

// imports

import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GlucoseDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "glucosedb";
    private static int DB_VERSION = 1;

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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertGlucose(SQLiteDatabase db, String dateStr,String timeStr, int glucose) {
        ContentValues glucoseValues = new ContentValues();
        glucoseValues.put("DATE", dateStr);
        glucoseValues.put("TIME", timeStr);
        glucoseValues.put("GLUCOSE", glucose);
        db.insert("GLUCOSE", null, glucoseValues);
    }

}
