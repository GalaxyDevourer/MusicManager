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
                new Song("Smells Like Teen Spirit", "Nirvana", "USA" ,"Nevermind", "1991", true),
                new Song("In Bloom", "Nirvana", "USA" ,"Nevermind", "1991", true),
                new Song("Been a Son", "Nirvana", "USA" ,"Bleach", "1989", false),
                new Song("Know", "System Of A Down", "Armenia-USA" ,"System of a Down", "1998", false),
                new Song("Mind", "System Of A Down", "Armenia-USA" ,"System of a Down", "1998", false),
                new Song("Peephole", "System Of A Down", "Armenia-USA" ,"System of a Down", "1998", false),
                new Song("Sugar", "System Of A Down", "Armenia-USA" ,"System of a Down", "1998", true),
                new Song("Needles", "System Of A Down", "Armenia-USA" ,"Toxicity", "2001", false),
                new Song("Toxicity", "System Of A Down", "Armenia-USA" ,"Toxicity", "2001", true),
                new Song("Liberate", "Disturbed", "USA" ,"Believe", "2002", true),
                new Song("Darkness", "Disturbed", "USA" ,"Believe", "2002", false),
                new Song("Guarded", "Disturbed", "USA" ,"Ten Thousand Fists", "2005", true),
                new Song("Numb", "Linkin Park", "USA" ,"Meteora", "2003", true),
                new Song("Blackout", "Linkin Park", "USA" ,"A Thousand Suns", "2010", false),
                new Song("Sorry for Now", "Linkin Park", "USA" ,"One More Light", "2017", false),
                new Song("It Has Begun", "Starset", "USA" ,"Transmissions", "2014", true),
                new Song("Everglow", "Starset", "USA" ,"Vessels", "2017", false),
                new Song("Mad World", "Gary Jules", "USA" ,"Trading Snakeoil for Wolftickets", "2001", false)
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

    public List<String> getAlbumsByBandName (String bandName) {
        List<String> albums = new ArrayList<>();

        for (Song song: songsList) {
            if (song.getBandName().equals(bandName)) {
                if (!albums.contains(song.getAlbum())) {
                    albums.add(song.getAlbum());
                }
            }
        }

        return albums;
    }
}
