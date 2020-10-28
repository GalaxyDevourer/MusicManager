package com.chmnu.groupmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

public class SongsDatabaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "song_db";
    private final static int DB_VERSION = 1;

    private static String SONG_TABLE = "songs";
    private static String SONG_NAME = "songName";
    private static String BAND_NAME = "bandName";
    private static String ALBUM_NAME = "album";
    private static String ALBUM_YEAR = "albumYear";
    private static String SINGLE = "single";

    public SongsDatabaseHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE songs (\n"
                + "id           INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "songName     TEXT(100) NOT NULL,\n"
                + "bandName     TEXT(100),\n"
                + "album        TEXT(100),\n"
                + "albumYear    TEXT(100),\n"
                + "single       BOOLEAN\n"
                + ");";

        sqLiteDatabase.execSQL(sqlQuery);
        fillData(sqLiteDatabase);
    }

    private void fillData (SQLiteDatabase db) {
        for (Song song: SongStorage.getSongsList() ) {
            insert(db, song);
        }
    }

    private void insert (SQLiteDatabase db, Song song) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(SONG_NAME, song.getSongName());
        contentValues.put(BAND_NAME, song.getBandName());
        contentValues.put(ALBUM_NAME, song.getAlbum());
        contentValues.put(ALBUM_YEAR, song.getAlbumYear());
        contentValues.put(SINGLE, song.getSingle());

        db.insert(SONG_TABLE, null, contentValues);
    }
}
