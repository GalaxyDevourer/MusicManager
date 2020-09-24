package com.chmnu.groupmanager.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.entities.BandStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

public class BandsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bands_list);

    }

    @Override
    protected void onStart () {
        super.onStart();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bands_list_menu, menu);

        StringBuilder bandsListMessage = new StringBuilder();

        ListView bandsList = findViewById(R.id.bands_list);
        for (int i=0; i < bandsList.getCount() ; i++){
            bandsListMessage.append(bandsList.getItemAtPosition(i)).append("\n");
        }

        MenuItem menuItem = menu.findItem(R.id.bands_list_menu_send);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_TEXT, bandsListMessage.toString());
        shareActionProvider.setShareIntent(intent);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bands_list_menu_add:
                startActivity(new Intent (this, AddDataActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
