package com.chmnu.groupmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;

public class BandsDatabaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "songs";
    private final static int DB_VERSION = 1;

    private static String BAND_TABLE = "bands";
    private static String BAND_NAME = "bandName";
    private static String BAND_COUNTRY = "bandCountry";
    private static String BAND_YEAR = "bandYear";

    public BandsDatabaseHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE bands (\n"
                + "id               INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "bandName         TEXT(100) NOT NULL,\n"
                + "bandCountry      TEXT(100),\n"
                + "bandYear         TEXT(100)\n"
                + ");";

        sqLiteDatabase.execSQL(sqlQuery);
        fillData(sqLiteDatabase);
    }

    private void fillData (SQLiteDatabase db) {
        for (Band band: BandStorage.getBandList() ) {
            insert(db, band);
        }
    }

    private void insert (SQLiteDatabase db, Band band) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BAND_NAME, band.getBandName());
        contentValues.put(BAND_COUNTRY, band.getBandCountry());
        contentValues.put(BAND_YEAR, band.getBandYear());

        db.insert(BAND_TABLE, null, contentValues);
    }

}
