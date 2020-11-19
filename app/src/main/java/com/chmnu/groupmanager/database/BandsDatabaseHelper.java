package com.chmnu.groupmanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;
import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

public class BandsDatabaseHelper extends SQLiteOpenHelper {

    private final static String DB_NAME = "band_db";
    private final static int DB_VERSION = 2;

    private static String BAND_TABLE = "bands";
    private static String BAND_NAME = "bandName";
    private static String BAND_COUNTRY = "bandCountry";
    private static String BAND_YEAR = "bandYear";

    private static String SONG_TABLE = "songs";
    private static String SONG_NAME = "songName";
    private static String ALBUM_NAME = "album";
    private static String ALBUM_YEAR = "albumYear";
    private static String SINGLE = "single";

    public BandsDatabaseHelper (Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        updateSchema(sqLiteDatabase, oldVersion);
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

        updateSchema(sqLiteDatabase, 0);

        fillDatabase(sqLiteDatabase);
    }

    private void fillDatabase (SQLiteDatabase db) {
        fillBands(db);
        fillSongs(db);
    }

    private void insertBand (SQLiteDatabase db, Band band) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(BAND_NAME, band.getBandName());
        contentValues.put(BAND_COUNTRY, band.getBandCountry());
        contentValues.put(BAND_YEAR, band.getBandYear());

        db.insert(BAND_TABLE, null, contentValues);
    }

    private void insertSong (SQLiteDatabase db, Song song) {
        db.execSQL("INSERT INTO " + SONG_TABLE + "(songName, bandName, album, albumYear, single, id_band) \n"
                + "select '" + song.getSongName() + "', '" + song.getBandName() + "', '" + song.getAlbum() + "', '"
                + song.getAlbumYear() + "', '" + song.getSingle() + "', id \n"
                + "from " + BAND_TABLE + " \n"
                + "where bandName = '" + song.getBandName() + "'");
    }

    private void fillBands (SQLiteDatabase db) {
        for (Band band: BandStorage.getBandList() ) {
            insertBand(db, band);
        }
    }

    private void fillSongs (SQLiteDatabase db) {
        for (Song song: SongStorage.getSongsList() ) {
            insertSong(db, song);
        }
    }

    private void updateSchema(SQLiteDatabase db, int oldVersion) {
        if (oldVersion < DB_VERSION) {
            db.execSQL("CREATE TABLE songs (\n"
                    + "id           INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "songName     TEXT(100) NOT NULL,\n"
                    + "bandName     TEXT(100),\n"
                    + "album        TEXT(100),\n"
                    + "albumYear    TEXT(100),\n"
                    + "single       BOOLEAN,\n"
                    + "id_band      INTEGER REFERENCES bands (id) ON DELETE RESTRICT \n"
                    + "                                           ON UPDATE RESTRICT \n"
                    + ");");

            fillSongs(db);
        }
    }
}
