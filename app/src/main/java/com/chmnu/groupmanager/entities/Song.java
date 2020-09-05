package com.chmnu.groupmanager.entities;

public class Song {
    private String songName;
    private String bandName;
    private String album;

    public Song(String songName, String bandName, String album) {
        this.songName = songName;
        this.bandName = bandName;
        this.album = album;
    }

    public String getSongName() {
        return songName;
    }

    public String getBandName() {
        return bandName;
    }

    public String getAlbum() {
        return album;
    }

    public String getSignature () {
        return bandName + " - " + songName + " [ " + album + " ]";
    }
}
