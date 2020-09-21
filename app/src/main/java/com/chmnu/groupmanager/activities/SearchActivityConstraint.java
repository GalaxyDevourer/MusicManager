package com.chmnu.groupmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;
import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

import java.util.List;

public class SearchActivityConstraint extends AppCompatActivity {

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
}
