package com.chmnu.groupmanager.activities;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import com.chmnu.groupmanager.models.entities.music.Band;
import com.chmnu.groupmanager.models.entities.music.Song;
import com.chmnu.groupmanager.models.entities.music.SongStorage;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class AddSongsActivity extends AppCompatActivity {

    private static String BAND_TABLE = "bands";
    private static String BAND_ID = "id";
    private static String BAND_NAME = "bandName";
    private static String BAND_COUNTRY = "bandCountry";
    private static String BAND_YEAR = "bandYear";

    private static String SONG_ID = "id";
    private static String SONG_TABLE = "songs";
    private static String SONG_NAME = "songName";
    private static String SONG_BAND_NAME = "bandName";
    private static String SONG_ALBUM_NAME = "album";
    private static String SONG_ALBUM_YEAR = "albumYear";
    private static String SONG_SINGLE = "single";
    private static String SONG_ID_BAND = "id_band";

    public static final String BAND_ID_NAME = "bandID";

    private Integer id_band;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        id_band = getIntent().getIntExtra(BAND_ID_NAME, 0);

        ArrayList<Band> bands = getBandsFromDB();

        Spinner bandsList = findViewById(R.id.bands_id_choice);
        ArrayAdapter<Band> bandAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bands);
        bandsList.setAdapter(bandAdapter);

        ArrayList<Song> songs = getSongsByBandFromDB();

        Spinner songsList = findViewById(R.id.songs_id_choice);
        ArrayAdapter<Song> songAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songs);
        songsList.setAdapter(songAdapter);
    }

    public void onAddSongManageSong (View view) {
        boolean is_update = ((CheckBox) findViewById(R.id.songs_use_check)).isChecked();

        if (is_update) updateSong();
        else addSong();
    }

    public void addSong () {
        String songName = ((EditText) findViewById(R.id.add_data_song_name)).getText().toString();
        String bandName = ((EditText) findViewById(R.id.add_data_song_band_name)).getText().toString();
        String album = ((EditText) findViewById(R.id.add_data_album_name)).getText().toString();
        String albumYear = ((EditText) findViewById(R.id.add_data_album_year)).getText().toString();
        Boolean single = ((CheckBox) findViewById(R.id.add_data_check_is_single)).isChecked();
        Integer id_band = ((Song)(((Spinner) findViewById(R.id.bands_id_choice)).getSelectedItem())).getId();

        Song song = new Song(songName,bandName,album,albumYear,single,id_band);
        new SongStorage().addSongHttp(song);

    }

    public void updateSong () {
        String songName = ((EditText) findViewById(R.id.add_data_song_name)).getText().toString();
        String bandName = ((EditText) findViewById(R.id.add_data_song_band_name)).getText().toString();
        String album = ((EditText) findViewById(R.id.add_data_album_name)).getText().toString();
        String albumYear = ((EditText) findViewById(R.id.add_data_album_year)).getText().toString();
        Boolean single = ((CheckBox) findViewById(R.id.add_data_check_is_single)).isChecked();
        Integer id_band = ((Song)(((Spinner) findViewById(R.id.bands_id_choice)).getSelectedItem())).getId();

        Spinner songSpinner = findViewById(R.id.songs_id_choice);
        Integer id = ((Song) songSpinner.getSelectedItem()).getId();

        Song song = new Song(id,songName,bandName,album,albumYear,single,id_band);
        new SongStorage().updateSongHttp(song);

    }

    public void onAddSongDeleteSong (View view) {
        Spinner id_song = findViewById(R.id.songs_id_choice);
        Song song = ((Song) id_song.getSelectedItem());

        new SongStorage().deleteSongHttp(song);
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

    private ArrayList<Song> getSongsByBandFromDB () {
        ArrayList<Song> songsList = new ArrayList<>();

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);

        String [] id = new String[] { id_band.toString() };
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(SONG_TABLE, new String[] {SONG_ID, SONG_NAME, SONG_BAND_NAME, SONG_ALBUM_NAME, SONG_ALBUM_YEAR, SONG_SINGLE, SONG_ID_BAND},
                    "id_band=?", id, null, null, SONG_ID_BAND);

            while (cursor.moveToNext()) {
                Song song = new Song(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getInt(5) > 0, cursor.getInt(6));
                songsList.add(song);
            }
            cursor.close();
            db.close();
        }
        catch (SQLException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }

        return songsList;
    }
}
