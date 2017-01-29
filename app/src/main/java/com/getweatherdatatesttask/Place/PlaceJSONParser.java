package com.getweatherdatatesttask.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaceJSONParser {
    public static List<Place> parsePlaceFromJson(String placeTextJSON) {
        List<Place> places = null;
        try {
            places = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(placeTextJSON);
            JSONArray placesJsonArray = jsonObject.getJSONArray("predictions");

            for (int i = 0; i < placesJsonArray.length(); i++) {
                JSONObject placeJsonObject = placesJsonArray.getJSONObject(i);
                String description = placeJsonObject.getString("description");
                String placeId = placeJsonObject.getString("place_id");

                Place place = new Place();
                place.setDescription(description);
                place.setPlaceId(placeId);

                places.add(place);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return places;
    }
}
