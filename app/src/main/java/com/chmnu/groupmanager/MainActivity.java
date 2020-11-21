package com.chmnu.groupmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.chmnu.groupmanager.activities.BandsListActivity;
import com.chmnu.groupmanager.activities.UsersListActivity;
import com.chmnu.groupmanager.activities.SearchActivityConstraint;
import com.chmnu.groupmanager.activities.ShowTracksActivity;
import com.chmnu.groupmanager.models.entities.music.BandStorage;

public class MainActivity extends AppCompatActivity {

    private BandStorage bandStorage = new BandStorage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerAlbum = findViewById(R.id.check_group_spinner);
        ListAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bandStorage.getBandNames());
        spinnerAlbum.setAdapter((SpinnerAdapter) adapter);
    }

    public void onShowBtnClick (View view) {
        Spinner spinner = findViewById(R.id.check_group_spinner);
        String bandName = spinner.getSelectedItem().toString();

        Intent intent = new Intent(this, ShowTracksActivity.class);
        intent.putExtra(ShowTracksActivity.BAND_NAME, bandName);

        startActivity(intent);
    }

    public void onSearchBtnClick (View view) {
        Spinner spinner = findViewById(R.id.check_group_spinner);
        String bandName = spinner.getSelectedItem().toString();

        Intent intent = new Intent(this, SearchActivityConstraint.class);
        intent.putExtra(ShowTracksActivity.BAND_NAME, bandName);

        startActivity(intent);
    }

    public void onShowListBtnClick (View view) {
        Intent intent = new Intent(this, BandsListActivity.class);
        startActivity(intent);
    }

    public void onJsonGetClick (View view) {
        Intent intent = new Intent(this, UsersListActivity.class);
        startActivity(intent);
    }

}