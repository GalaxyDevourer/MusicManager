package com.chmnu.groupmanager.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.database.MusicDatabaseHelper;
import com.chmnu.groupmanager.models.entities.music.Band;
import com.chmnu.groupmanager.models.entities.music.BandStorage;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

public class BandsListActivity extends AppCompatActivity {

    private static String BAND_TABLE = "bands";
    private static String BAND_ID = "id";
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

        //ArrayList<Band> bandsList = getDataDB();
        ArrayList<Band> bandsList = new BandStorage().getBandsHttp();

        ListView bandsListView = findViewById(R.id.bands_list);
        ArrayAdapter<Band> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bandsList);
        bandsListView.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = (adapterView, view, i, l) -> {
            Band band = (Band) adapterView.getItemAtPosition(i);

            Intent intent = new Intent(BandsListActivity.this, SearchActivity.class);
            intent.putExtra(SearchActivity.BAND_ID, band.getId());

            startActivity(intent);
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
                Intent intent = new Intent(this, AddBandsActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Band> getDataDB () {
        ArrayList<Band> bandsList = new ArrayList<>();

        SQLiteOpenHelper sqLiteOpenHelper = new MusicDatabaseHelper(this);

        try {
            SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(BAND_TABLE, new String[] {BAND_ID, BAND_NAME, BAND_COUNTRY, BAND_YEAR},
                    null, null, null, null, BAND_YEAR);

            while (cursor.moveToNext()) {
                bandsList.add(new Band(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
            cursor.close();
            db.close();
        }
        catch (SQLException ex) {
            Toast toast = Toast.makeText(this, "This DB is not available", Toast.LENGTH_SHORT);
            toast.show();

            ex.getStackTrace();
        }

        return bandsList;
    }
}
