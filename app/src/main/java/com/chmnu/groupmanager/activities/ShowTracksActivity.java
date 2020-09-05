package com.chmnu.groupmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.chmnu.groupmanager.R;
import com.chmnu.groupmanager.entities.Song;
import com.chmnu.groupmanager.entities.SongStorage;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ShowTracksActivity extends AppCompatActivity {

    public static final String BAND_NAME = "bandName";

    private float textSize = 0;
    private int timeSecond = 0;

    private boolean isRunning = true;

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

        textSize = textView.getTextSize();
        if (savedInstanceState != null) {
            textSize = savedInstanceState.getFloat("textSize");
            timeSecond = savedInstanceState.getInt("timeSecond");
        }

        runTimer();
    }

    @Override
    protected void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putFloat("textSize", textSize);
        outState.putInt("timeSecond", timeSecond);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }

    private void runTimer () {
        final TextView timer = findViewById(R.id.timer);
        final Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper()));

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = timeSecond/3600;
                int min = (timeSecond%3600)/60;
                int sec = timeSecond%60;

                timer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, min, sec));
                if (isRunning) timeSecond++;

                handler.postDelayed(this, 1000);
            }
        });

    }

    public void onSendBtnClick (View view) {
        TextView textView = findViewById(R.id.list_songs_text);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT, "My tracks");

        startActivity(intent);
    }

    public void onPlusBtnClick (View view) {
        textSize *= 1.05;
        TextView textView = findViewById(R.id.list_songs_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void onMinusBtnClick (View view) {
        textSize /= 1.05;
        TextView textView = findViewById(R.id.list_songs_text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }
}