package com.chmnu.groupmanager.activities;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.database.BandsDatabaseHelper;
import com.chmnu.groupmanager.entities.Band;
import com.chmnu.groupmanager.entities.BandStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

public class BandsListActivity extends AppCompatActivity {

    private static String BAND_TABLE = "bands";
    private static String BAND_NAME = "bandName";
    private static String BAND_COUNTRY = "bandCountry";
    private static String BAND_YEAR = "bandYear";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bands_list);

    }

    @Override
    protected void onStart () {
        super.onStart();

        getDataDB();

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

    private ArrayList<Band> getDataDB () {
        ArrayList<Band> bandsList = new ArrayList<>();

        SQLiteOpenHelper sqLiteOpenHelper = new BandsDatabaseHelper(this);

        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(BAND_TABLE, new String[] {BAND_NAME, BAND_COUNTRY, BAND_YEAR},
                    null, null, null, null, null);
            cursor.close();
        }
        catch (SQLException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();
        }

        return bandsList;
    }
}
