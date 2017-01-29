package com.getweatherdatatesttask.Place;

import com.google.android.gms.maps.model.LatLng;

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
            JSONObject rootJsonObject = new JSONObject(placeTextJSON);
            JSONArray placesJsonArray = rootJsonObject.getJSONArray("predictions");

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

    public static LatLng parsePlaceCoordinatesFromJson(String placeDescriptionJson) {

        LatLng placeCoordinate = null;
        try {
            JSONObject rootJsonObject = new JSONObject(placeDescriptionJson);
            JSONObject resultJsonObject = rootJsonObject.getJSONObject("result");
            JSONObject geometryJsonObject = resultJsonObject.getJSONObject("geometry");
            JSONObject locationJsonObject = geometryJsonObject.getJSONObject("location");
            Double lat = Double.valueOf(locationJsonObject.getString("lat"));
            Double lng = Double.valueOf(locationJsonObject.getString("lng"));
            placeCoordinate = new LatLng(lat, lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeCoordinate;
    }
}