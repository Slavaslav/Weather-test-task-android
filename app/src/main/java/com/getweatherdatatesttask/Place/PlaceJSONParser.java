package com.getweatherdatatesttask.Place;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class PlaceJSONParser {

    private static final String PREDICTIONS = "predictions";
    private static final String DESCRIPTION = "description";
    private static final String PLACE_ID = "place_id";
    private static final String RESULT = "result";
    private static final String GEOMETRY = "geometry";
    private static final String LOCATION = "location";
    private static final String LAT = "lat";
    private static final String LNG = "lng";

    public static List<Place> parsePlaceFromJson(String placeTextJSON) {
        List<Place> places = null;
        try {
            places = new ArrayList<>();
            JSONObject rootJsonObject = new JSONObject(placeTextJSON);
            JSONArray placesJsonArray = rootJsonObject.getJSONArray(PREDICTIONS);

            for (int i = 0; i < placesJsonArray.length(); i++) {
                JSONObject placeJsonObject = placesJsonArray.getJSONObject(i);
                String description = placeJsonObject.getString(DESCRIPTION);
                String placeId = placeJsonObject.getString(PLACE_ID);

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
            JSONObject resultJsonObject = rootJsonObject.getJSONObject(RESULT);
            JSONObject geometryJsonObject = resultJsonObject.getJSONObject(GEOMETRY);
            JSONObject locationJsonObject = geometryJsonObject.getJSONObject(LOCATION);
            Double lat = Double.valueOf(locationJsonObject.getString(LAT));
            Double lng = Double.valueOf(locationJsonObject.getString(LNG));
            placeCoordinate = new LatLng(lat, lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return placeCoordinate;
    }
}