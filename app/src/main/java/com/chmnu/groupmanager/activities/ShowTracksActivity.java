package com.chmnu.groupmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.database.MusicDatabaseHelper;

public class ShowTracksActivity extends AppCompatActivity {

    public static final String BAND_NAME = "bandName";
    public static final String TRACKS_LIST_NAME = "bandsList";
    public static final String BAND_ID_NAME = "bandID";

    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tracks);

        Intent intent = getIntent();
        int id_band = intent.getIntExtra(BAND_ID_NAME, 0);

        ListView bandsListView = findViewById(R.id.tracks_list);
        SimpleCursorAdapter cursorAdapter = getTracksFromDB(id_band);
        if (cursorAdapter != null) {
            System.out.println("add items to listview");
            bandsListView.setAdapter(cursorAdapter);
        }

    }

    private SimpleCursorAdapter getTracksFromDB (int id_band) {
        SimpleCursorAdapter listAdapter = null;

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);
        try {
            db = sqLiteOpenHelper.getReadableDatabase();
            cursor = db.rawQuery("select s.id _id, songName, s.bandName, album, albumYear, single, id_band\n" +
                    "from songs s inner join bands b on s.id_band = b.id\n" +
                    "where b.id = ?", new String[] {Integer.toString(id_band)});

            listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                    new String[] {"songName", "bandName", "album"}, new int[] {android.R.id.text1}, 0);
        }
        catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }

        return listAdapter;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    public void onSendBtnClick (View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        StringBuilder tracksListMessage = new StringBuilder();

        ListView tracksList = findViewById(R.id.tracks_list);
        for (int i=0; i < tracksList.getCount() ; i++){
            tracksListMessage.append(tracksList.getItemAtPosition(i)).append("\n");
        }

        intent.putExtra(Intent.EXTRA_TEXT, tracksListMessage.toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, "My tracks");

        startActivity(intent);
    }

}