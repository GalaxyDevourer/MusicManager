package com.chmnu.groupmanager.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BandStorage {

    private static List<Band> bandList = new ArrayList<>(
            Arrays.asList(
                    new Band("Nirvana", "1991"),
                    new Band("System Of A Down", "2001"),
                    new Band("Disturbed", "2002"),
                    new Band("Linkin Park","2003"),
                    new Band("Starset", "2014"),
                    new Band("Gary Jules", "2001")
            )
    );

    public static void addBand (Band band) {
        bandList.add(band);
    }

    public List<Band> getBandList() {
        return bandList;
    }

    public void setBandList(List<Band> bandList) {
        BandStorage.bandList = bandList;
    }

    public ArrayList<String> getBandNames () {
        ArrayList<String> bandNames = new ArrayList<>();
        for (Band band: bandList) {
            bandNames.add(band.getBandName());
        }

        return bandNames;
    }

    public Band getBandByName(String bandName) {
        for (Band band: bandList) {
            if (band.getBandName().equals(bandName)) {
                return band;
            }
        }
        return null;
    }

}
