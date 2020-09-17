package com.chmnu.groupmanager.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;
import com.chmnu.groupmanager.entities.SongStorage;

public class SearchActivity extends AppCompatActivity {

    public static final String BAND_NAME = "bandName";
    private BandStorage bandStorage;
    private SongStorage songStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String bandName = intent.getStringExtra(BAND_NAME);

        bandStorage = new BandStorage();
        songStorage = new SongStorage();

        Band currentBand = bandStorage.getBandByName(bandName);
        TextView infoband = findViewById(R.id.group_information_label);
        infoband.setText(currentBand.getSignature());

        EditText bandNameEdit = findViewById(R.id.band_name_enter);
        bandNameEdit.setText(bandName);

        Spinner spinnerAlbum = findViewById(R.id.album_name_choice);
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, songStorage.getAlbumsByBandName(bandName));
        spinnerAlbum.setAdapter((SpinnerAdapter) adapter);
        spinnerAlbum.setDropDownHorizontalOffset(20);
        spinnerAlbum.setDropDownVerticalOffset(10);

        setBandAvatar(bandName);
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
}