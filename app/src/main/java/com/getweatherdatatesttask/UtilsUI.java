package com.getweatherdatatesttask;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

class UtilsUI {

    public static void hideSoftKeyboard(MapsActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View view = activity.getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}