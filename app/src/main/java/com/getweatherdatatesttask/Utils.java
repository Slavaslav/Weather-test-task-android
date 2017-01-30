package com.getweatherdatatesttask;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

class Utils {

    public static void hideSoftKeyboard(MapsActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void showToast(MapsActivity activity, String toastText, int toastLong) {
        Toast toast = Toast.makeText(activity.getApplicationContext(), toastText, toastLong);
        toast.show();
    }

    public static boolean isNetworkConnected(MapsActivity activity) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnectedOrConnecting();
        }
        return isConnected;
    }
}