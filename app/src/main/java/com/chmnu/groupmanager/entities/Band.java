package com.chmnu.groupmanager.entities;

public class Band {
    private String bandName;
    private String yearGroup;

    public Band(String bandName, String yearGroup) {
        this.bandName = bandName;
        this.yearGroup = yearGroup;
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public String getYearGroup() {
        return yearGroup;
    }

    public void setYearGroup(String yearGroup) {
        this.yearGroup = yearGroup;
    }

    public String getSignature () {
        return bandName + " [ " + yearGroup + " ]";
    }
}
