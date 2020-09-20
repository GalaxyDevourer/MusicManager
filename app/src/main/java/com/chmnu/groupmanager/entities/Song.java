package com.chmnu.groupmanager.entities;

import android.text.BoringLayout;

public class Song {
    private String songName;
    private String bandName;
    private String bandCountry;
    private String album;
    private String albumYear;
    private Boolean single;

    public Song(String songName, String bandName, String bandCountry, String album, String albumYear, Boolean single) {
        this.songName = songName;
        this.bandName = bandName;
        this.bandCountry = bandCountry;
        this.album = album;
        this.albumYear = albumYear;
        this.single = single;
    }

    public Song(String bandName, String album, Boolean single) {
        this.bandName = bandName;
        this.album = album;
        this.single = single;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getBandCountry() {
        return bandCountry;
    }

    public void setBandCountry(String bandCountry) {
        this.bandCountry = bandCountry;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumYear() {
        return albumYear;
    }

    public void setAlbumYear(String albumYear) {
        this.albumYear = albumYear;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }

    public String getSignature () {
        return bandName + " [" + bandCountry + "]" + " - " + songName + " [ " + album + " " + albumYear + " ]";
    }

    public Boolean customEquals (Song song) {
        return bandName.equals(song.bandName) && album.equals(song.album) && single.equals(song.single);
    }
}