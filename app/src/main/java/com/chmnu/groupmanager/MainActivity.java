package com.chmnu.groupmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClick (View view) {
        Spinner spinner = findViewById(R.id.check_group_spinner);
        String bandName = spinner.getSelectedItem().toString();

        StringBuilder songList = new StringBuilder();
        List<Song> songsList = new SongStorage().getByBandName(bandName);
        for(Song song: songsList) {
            songList.append(song.getSignature()).append("\n");
        }

        TextView textView = findViewById(R.id.list_songs_text);
        textView.setText(songList);
    }
}