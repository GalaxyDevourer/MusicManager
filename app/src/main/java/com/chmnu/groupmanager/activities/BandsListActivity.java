package com.chmnu.groupmanager.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.entities.BandStorage;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class BandsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bands_list);

        BandStorage bandStorage = new BandStorage();
        ArrayList<String> arrayList = bandStorage.getBandNames();

        ListView bandsListView = findViewById(R.id.bands_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        bandsListView.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String band = adapterView.getItemAtPosition(i).toString();

                Intent intent = new Intent(BandsListActivity.this, SearchActivity.class);
                intent.putExtra(SearchActivity.BAND_NAME, band);

                startActivity(intent);
            }
        };
        bandsListView.setOnItemClickListener(listener);
    }

}
