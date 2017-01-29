package com.getweatherdatatesttask.Place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.getweatherdatatesttask.HttpRequestClient;
import com.getweatherdatatesttask.R;

import java.util.ArrayList;
import java.util.List;

public class PlacesSearchAutoCompleteAdapter extends BaseAdapter implements Filterable {

    private final Context context;
    private List<Place> places = new ArrayList<>();

    public PlacesSearchAutoCompleteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return places.size();
    }

    @Override
    public Object getItem(int i) {
        return places.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_search_auto_complete, viewGroup, false);
        }
        ((TextView) view.findViewById(R.id.auto_complete_text_view)).setText(places.get(i).getDescription());
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    String getPlacesJson = HttpRequestClient.getPlacesDataByQuery(constraint.toString());
                    if (getPlacesJson.equals("error")) {
                        Toast toast = new Toast(context);
                        toast.setText("Something went wrong. Please, try again");
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        places = PlaceJSONParser.parsePlaceFromJson(getPlacesJson);
                    }
                    filterResults.values = places;
                    filterResults.count = places.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}