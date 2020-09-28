package com.chmnu.groupmanager.entities;

public class Band {
    private String bandName;
    private String bandCountry;
    private String yearGroup;

    public Band(String bandName, String bandCountry, String yearGroup) {
        this.bandName = bandName;
        this.bandCountry = bandCountry;
        this.yearGroup = yearGroup;
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
        return yearGroup;
    }

    public void setYearGroup(String yearGroup) {
        this.yearGroup = yearGroup;
    }

    public String getSignature () {
        return bandName + " [ " + yearGroup + " ]";
    }
}
