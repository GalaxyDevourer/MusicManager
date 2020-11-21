package com.chmnu.groupmanager.activities;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.database.MusicDatabaseHelper;
import com.chmnu.groupmanager.entities.Band;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class AddBandsActivity extends AppCompatActivity {

    private static String BAND_TABLE = "bands";
    private static String BAND_ID = "id";
    private static String BAND_NAME = "bandName";
    private static String BAND_COUNTRY = "bandCountry";
    private static String BAND_YEAR = "bandYear";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_band);

        ArrayList<Band> bands = getBandsFromDB();

        Spinner bandsList = findViewById(R.id.add_data_band_choice);
        ArrayAdapter<Band> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bands);
        bandsList.setAdapter(adapter);
    }

    private ArrayList<Band> getBandsFromDB () {
        ArrayList<Band> bandsList = new ArrayList<>();

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);

        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(BAND_TABLE, new String[] {BAND_ID, BAND_NAME, BAND_COUNTRY, BAND_YEAR},
                    null, null, null, null, BAND_YEAR);

            while (cursor.moveToNext()) {
                bandsList.add(new Band(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
            cursor.close();
            db.close();
        }
        catch (SQLException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }

        return bandsList;
    }

    public void onAddBandManageBand (View view) {
        boolean is_update = ((CheckBox) findViewById(R.id.add_data_use_check)).isChecked();

        if (is_update) updateBand();
        else addBand();
    }

    public void addBand () {
        String bandName = ((EditText) findViewById(R.id.add_data_band_name)).getText().toString();
        String bandYear = ((EditText) findViewById(R.id.add_data_band_year)).getText().toString();
        String bandCountry = ((EditText) findViewById(R.id.add_data_band_country)).getText().toString();

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("bandName", bandName);
            contentValues.put("bandYear", bandYear);
            contentValues.put("bandCountry", bandCountry);

            db.insert(BAND_TABLE, null, contentValues);
            db.close();

            Toast toast = Toast.makeText(this, "Band successfully added!", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }
    }

    public void updateBand () {
        String bandName = ((EditText) findViewById(R.id.add_data_band_name)).getText().toString();
        String bandYear = ((EditText) findViewById(R.id.add_data_band_year)).getText().toString();
        String bandCountry = ((EditText) findViewById(R.id.add_data_band_country)).getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(BAND_NAME,bandName);
        contentValues.put(BAND_YEAR,bandYear);
        contentValues.put(BAND_COUNTRY,bandCountry);

        Spinner band = findViewById(R.id.add_data_band_choice);
        String id = ((Band) band.getSelectedItem()).getId().toString();

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            db.update(BAND_TABLE, contentValues, "id=?", new String[] {id});
            db.close();

            Toast toast = Toast.makeText(this, "Band successfully updated!", Toast.LENGTH_SHORT);
            toast.show();
        }
        catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }
    }

}
