package com.chmnu.groupmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.chmnu.groupmanager.activities.ShowTracksActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Intent intent = new Intent(this, ShowTracksActivity.class);
        intent.putExtra(ShowTracksActivity.BAND_NAME, bandName);

        startActivity(intent);

    }
}