package com.chmnu.groupmanager.models.entities.music;

import com.chmnu.groupmanager.models.entities.http.User;
import com.chmnu.groupmanager.models.utils.http.HttpDataGetter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BandStorage {

    private static List<Band> bandList = new ArrayList<>(
            Arrays.asList(
                    new Band("Nirvana", "USA", "1991"),
                    new Band("System Of A Down","Armenia-USA","2001"),
                    new Band("Disturbed", "USA", "2002"),
                    new Band("Linkin Park", "USA", "2003"),
                    new Band("Starset", "USA","2014"),
                    new Band("Gary Jules", "USA","2001")
            )
    );

    public static void addBand (Band band) {
        bandList.add(band);
    }

    public static List<Band> getBandList() {
        return bandList;
    }

    public static void setBandList(List<Band> bandList) {
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

    public ArrayList<Band> getBandsHttp () {
        ArrayList<Band> bandArrayList = new ArrayList<>();
        String response = new HttpDataGetter("http://192.168.1.127/api/?action=get_bands_list").getData();

        try {
            JSONArray jsonArray = new JSONArray(response);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Band band = new Band(
                        obj.getInt("id"),
                        obj.getString("bandName"),
                        obj.getString("bandCountry"),
                        obj.getString("bandYear"));
                bandArrayList.add(band);
            }
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }

        return  bandArrayList;
    }

    public Band getBandHttp (Integer id) {
        Band band = new Band();
        String response = new HttpDataGetter("http://192.168.1.127/api/?action=get_band&id_band=" + id).getData();

        try {
            JSONObject obj = new JSONObject(response);
            band = new Band(
                    obj.getInt("id"),
                    obj.getString("bandName"),
                    obj.getString("bandCountry"),
                    obj.getString("bandYear"));
        }
        catch (JSONException jex) {
            jex.printStackTrace();
        }

        return band;
    }

}
