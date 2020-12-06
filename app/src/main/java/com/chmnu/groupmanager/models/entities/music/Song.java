package com.chmnu.groupmanager.models.entities.music;

public class Song {
    private Integer id;
    private String songName;
    private String bandName;
    private String album;
    private String albumYear;
    private Boolean single;
    private Integer id_band;

    public Song() {};

    public Song(String songName, String bandName, String album, String albumYear, Boolean single, Integer id_band) {
        this.songName = songName;
        this.bandName = bandName;
        this.album = album;
        this.albumYear = albumYear;
        this.single = single;
        this.id_band = id_band;
    }

    public Song(Integer id, String songName, String bandName, String album, String albumYear,
                Boolean single, Integer id_band) {
        this(songName, bandName, album, albumYear, single);
        this.id = id;
        this.id_band = id_band;
    }

    public Song(String songName, String bandName, String album, String albumYear, Boolean single) {
        this.songName = songName;
        this.bandName = bandName;
        this.album = album;
        this.albumYear = albumYear;
        this.single = single;
    }

    public Song(String bandName, String album, Boolean single) {
        this.bandName = bandName;
        this.album = album;
        this.single = single;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getId_band() {
        return id_band;
    }

    public void setId_band(Integer id_band) {
        this.id_band = id_band;
    }

    public String getSignature () {
        return bandName + " - " + songName + " [ " + album + " " + albumYear + " ]";
    }

    public Boolean customEquals (Song song) {
        return bandName.equals(song.bandName) && album.equals(song.album) && single.equals(song.single);
    }

    @Override
    public String toString() {
        return (songName != null) ? songName : "Null";
    }
}