package com.chmnu.groupmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.database.MusicDatabaseHelper;
import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;
import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    public static String BAND_ID = "id";
    public static String BAND_NAME = "bandName";
    private static String BAND_TABLE = "bands";
    private static String BAND_COUNTRY = "bandCountry";
    private static String BAND_YEAR = "bandYear";

    public static final String BAND_ID_NAME = "bandID";

    private BandStorage bandStorage;
    private SongStorage songStorage;

    private Band selectedBand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        int id = intent.getIntExtra(BAND_ID, 0);

        selectedBand = getDataDB(id);
        String bandName = selectedBand.getBandName();

        bandStorage = new BandStorage();
        songStorage = new SongStorage();

        TextView infoband = findViewById(R.id.group_information_label);
        infoband.setText(selectedBand.getSignature());

        EditText bandNameEdit = findViewById(R.id.band_name_enter);
        bandNameEdit.setText(bandName);

        Spinner spinnerAlbum = findViewById(R.id.album_name_choice);
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, songStorage.getAlbumsByBandName(bandName));
        spinnerAlbum.setAdapter((SpinnerAdapter) adapter);

        setBandAvatar(bandName);
    }

    private Band getDataDB (int id) {
        Band selectedBand = null;

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);
        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(BAND_TABLE, new String[] {BAND_ID, BAND_NAME, BAND_COUNTRY, BAND_YEAR},
                    BAND_ID + "=?", new String[] {Integer.toString(id)}, null, null, null);

            if (cursor.moveToNext()) {
                selectedBand = new Band(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            }
            else {
                Toast toast = Toast.makeText(this, "No band with this id = " + id, Toast.LENGTH_SHORT);
                toast.show();
            }

            cursor.close();
            db.close();
        }
        catch (SQLException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();
        }

        return selectedBand;
    }

    public void onSearchShowBtnClick(View view) {
        /*
        ArrayList<String> bandsList = new ArrayList<>();

        EditText nameBand = findViewById(R.id.band_name_enter);
        Spinner albumName = findViewById(R.id.album_name_choice);
        RadioButton isEarly = findViewById(R.id.radio_early);
        CheckBox isSingle = findViewById(R.id.check_is_single);

        boolean radio = false;
        if (isEarly.isChecked()) {
            radio = true;
        }

        boolean checkBox = false;
        if (isSingle.isChecked()) {
            checkBox = true;
        }

        List<Song> tracksList = new SongStorage().getSearchedSongs(nameBand.getText().toString(), albumName.getSelectedItem().toString(), radio, checkBox );
        for (Song song: tracksList) {
            bandsList.add(song.getSignature());
        }
        if (tracksList.isEmpty()) {
            bandsList.add("Sorry, but there is no songs for your request :(");
        }

        */

        Intent intent = new Intent(this, ShowTracksActivity.class);
        intent.putExtra(ShowTracksActivity.BAND_ID_NAME, selectedBand.getId());

        startActivity(intent);
    }

    public void onFindBtnClick (View view) {
        StringBuilder message = new StringBuilder();

        EditText nameBand = findViewById(R.id.band_name_enter);
        Spinner albumName = findViewById(R.id.album_name_choice);
        RadioButton isEarly = findViewById(R.id.radio_early);
        CheckBox isSingle = findViewById(R.id.check_is_single);

        boolean radio = false;
        if (isEarly.isChecked()) {
            radio = true;
        }

        boolean checkBox = false;
        if (isSingle.isChecked()) {
            checkBox = true;
        }

        List<Song> songList = new SongStorage().getSearchedSongs(nameBand.getText().toString(), albumName.getSelectedItem().toString(), radio, checkBox );
        for (Song song: songList) {
            message.append(song.getSignature()).append("\n");
        }

        Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show();
    }

    public void onCheckIsSingle (View view) {
        CheckBox isSingle = findViewById(R.id.check_is_single);
        CheckBox notSingle = findViewById(R.id.check_is_not_single);

        if (isSingle.isChecked()) notSingle.setChecked(false);
    }

    public void onCheckIsNotSingle (View view) {
        CheckBox isSingle = findViewById(R.id.check_is_single);
        CheckBox notSingle = findViewById(R.id.check_is_not_single);

        if (notSingle.isChecked()) isSingle.setChecked(false);
    }

    private void setBandAvatar (String bandName) {
        ImageView image = findViewById(R.id.band_avatar);
        int id = getResources().getIdentifier(bandNameValidator(bandName), "drawable", getPackageName());
        if (id != 0) {
            image.setImageDrawable(getDrawable(id));
        }
        else image.setImageDrawable(getDrawable(getResources().getIdentifier("not_found", "drawable", getPackageName())));
    }

    private String bandNameValidator (String bandName) {
        String validName = bandName.toLowerCase();
        return validName.replace(" ", "_");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu_add:
                Intent intent = new Intent(this, AddSongsActivity.class);
                intent.putExtra(ShowTracksActivity.BAND_ID_NAME, selectedBand.getId());
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSearchBandDelete (View view) {
        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);
        int id_band = selectedBand.getId();

        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            db.delete("bands", "id=?", new String[] {Integer.toString(id_band)});

            Intent intent = new Intent(this, BandsListActivity.class);
            startActivity(intent);
        }
        catch (SQLiteException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }
    }
}