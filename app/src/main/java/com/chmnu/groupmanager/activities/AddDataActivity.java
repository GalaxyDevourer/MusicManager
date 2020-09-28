package com.chmnu.groupmanager.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;
import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

public class AddDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

    }

    public void addDataBand (View view) {
        EditText bandName = findViewById(R.id.add_data_band_name);
        EditText bandYear = findViewById(R.id.add_data_band_year);
        EditText bandCountry = findViewById(R.id.add_data_band_country);

        String bandName_ = bandName.getText().toString();
        String bandYear_ = bandYear.getText().toString();
        String bandCountry_ = bandCountry.getText().toString();

        Band band = new Band(bandName_, bandCountry_, bandYear_);
        BandStorage.addBand(band);

        NavUtils.navigateUpFromSameTask(this);
    }

    public void addDataTrack (View view) {
        EditText songName = findViewById(R.id.add_data_song_name);
        EditText bandName = findViewById(R.id.add_data_song_band_name);
        EditText songAlbum = findViewById(R.id.add_data_album_name);
        EditText songAlbumYear = findViewById(R.id.add_data_album_year);
        CheckBox isSingle = findViewById(R.id.add_data_check_is_single);

        String songName_ = songName.getText().toString();
        String bandName_ = bandName.getText().toString();
        String songAlbumName_ = songAlbum.getText().toString();
        String songAlbumYear_ = songAlbumYear.getText().toString();
        Boolean isSingle_ = isSingle.isChecked();

        Song song = new Song(songName_, bandName_, songAlbumName_, songAlbumYear_, isSingle_);
        SongStorage.addSong(song);

        NavUtils.navigateUpFromSameTask(this);
    }
}
