package com.chmnu.groupmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

import java.util.List;

public class ShowTracksActivity extends AppCompatActivity {

    public static final String BAND_NAME = "bandName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tracks);

        Intent intent = getIntent();
        String bandName = intent.getStringExtra(BAND_NAME);

        StringBuilder songList = new StringBuilder();
        List<Song> songsList = new SongStorage().getByBandName(bandName);
        for(Song song: songsList) {
            songList.append(song.getSignature()).append("\n");
        }

        TextView textView = findViewById(R.id.list_songs_text);
        textView.setText(songList);

    }

    public void onSendBtnClick (View view) {
        TextView textView = findViewById(R.id.list_songs_text);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, "My tracks");

        startActivity(intent);
    }
}