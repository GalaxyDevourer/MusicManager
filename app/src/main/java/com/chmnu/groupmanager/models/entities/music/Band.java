package com.chmnu.groupmanager.models.entities.music;

public class Band {
    private Integer id;
    private String bandName;
    private String bandCountry;
    private String bandYear;

    public Band () {};

    public Band(Integer id, String bandName, String bandCountry, String bandYear) {
        this(bandName, bandCountry, bandYear);
        this.id = id;
    }

    public Band(String bandName, String bandCountry, String bandYear) {
        this.bandName = bandName;
        this.bandCountry = bandCountry;
        this.bandYear = bandYear;
    }

    public Integer getId() {
        return id;
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

    public String getBandYear() {
        return bandYear;
    }

    public void setBandYear(String bandYear) {
        this.bandYear = bandYear;
    }

    public String getSignature () {
        return bandName + " [ " + bandYear + " ]";
    }

    @Override
    public String toString() {
        return (bandName != null) ? bandName : "Null";
    }
}
