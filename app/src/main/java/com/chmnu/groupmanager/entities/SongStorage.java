package com.chmnu.groupmanager.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongStorage {

    private static List<Song> songsList = new ArrayList<>(
        Arrays.asList(
            new Song("Smells Like Teen Spirit", "Nirvana" ,"Nevermind", "1991", true),
            new Song("In Bloom", "Nirvana" ,"Nevermind", "1991", true),
            new Song("Been a Son", "Nirvana" ,"Bleach", "1989", false),
            new Song("Know", "System Of A Down" ,"System of a Down", "1998", false),
            new Song("Mind", "System Of A Down" ,"System of a Down", "1998", false),
            new Song("Peephole", "System Of A Down" ,"System of a Down", "1998", false),
            new Song("Sugar", "System Of A Down" ,"System of a Down", "1998", true),
            new Song("Needles", "System Of A Down" ,"Toxicity", "2001", false),
            new Song("Toxicity", "System Of A Down" ,"Toxicity", "2001", true),
            new Song("Liberate", "Disturbed" ,"Believe", "2002", true),
            new Song("Darkness", "Disturbed" ,"Believe", "2002", false),
            new Song("Guarded", "Disturbed" ,"Ten Thousand Fists", "2005", true),
            new Song("Numb", "Linkin Park" ,"Meteora", "2003", true),
            new Song("Blackout", "Linkin Park" ,"A Thousand Suns", "2010", false),
            new Song("Sorry for Now", "Linkin Park" ,"One More Light", "2017", false),
            new Song("It Has Begun", "Starset" ,"Transmissions", "2014", true),
            new Song("Everglow", "Starset" ,"Vessels", "2017", false),
            new Song("Mad World", "Gary Jules" ,"Trading Snakeoil for Wolftickets", "2001", false)
        )
    );

    public static List<Song> getSongsList() {
        return getSongsList("");
    }

    public static List<Song> getSongsList(String bandName) {
        List<Song> songs = new ArrayList<>();

        for (Song song: songsList) {
            if (song.getBandName().equals(bandName) || bandName.equals("")) {
                songs.add(song);
            }
        }

        return songs;
    }

    public static void setSongsList(List<Song> songsList) {
        SongStorage.songsList = songsList;
    }

    public static void addSong (Song song) {
        songsList.add(song);
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

    public ArrayList<Song> getSearchedSongs (String bandName, String albumName, Boolean isEarly, Boolean isSingle) {
        ArrayList<Song> songs = new ArrayList<>();
        Song signature_song = new Song(bandName, albumName, isSingle);

        for (Song song: songsList) {
            if (song.customEquals(signature_song)) {
                if (isEarly(song.getAlbumYear()) && isEarly) {
                    songs.add(song);
                }
                else if (!isEarly(song.getAlbumYear()) && !isEarly) {
                    songs.add(song);
                }
            }
        }

        return songs;
    }

    public Boolean isEarly (String year) {
        return Integer.parseInt(year) < 2000;
    }
}
