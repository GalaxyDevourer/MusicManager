package com.chmnu.groupmanager.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongStorage {
    private List<Song> songsList;

    public SongStorage() {
        this.songsList = setSongs();
    }

    private List<Song> setSongs () {
        return Arrays.asList(
                new Song("Smells Like Teen Spirit", "Nirvana", "Nevermind"),
                new Song("In Bloom", "Nirvana", "Nevermind"),
                new Song("Been a Son", "Nirvana", "Bleach"),
                new Song("Know", "System Of A Down", "System of a Down"),
                new Song("Mind", "System Of A Down", "System of a Down"),
                new Song("Peephole", "System Of A Down", "System of a Down"),
                new Song("Darts", "System Of A Down", "System of a Down"),
                new Song("Needles", "System Of A Down", "Toxicity"),
                new Song("ATWA", "System Of A Down", "Toxicity"),
                new Song("Liberate", "Disturbed", "Believe"),
                new Song("Darkness", "Disturbed", "Believe"),
                new Song("Guarded", "Disturbed", "Ten Thousand Fists"),
                new Song("Numb", "Linkin Park", "Meteora"),
                new Song("Blackout", "Linkin Park", "A Thousand Suns"),
                new Song("Sorry for Now", "Linkin Park", "One More Light"),
                new Song("It Has Begun", "Starset", "Transmissions"),
                new Song("Everglow", "Starset", "Vessels")
        );
    }


    public List<Song> getByBandName(String bandName) {
        List<Song> songs = new ArrayList<>();

        for (Song song: songsList) {
            if (song.getBandName().equals(bandName)) {
                songs.add(song);
            }
        }

        return songs;
    }
}
