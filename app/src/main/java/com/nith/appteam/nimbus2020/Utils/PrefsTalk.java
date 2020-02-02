package com.nith.appteam.nimbus2020.Utils;

import android.app.Activity;
import android.content.SharedPreferences;

public class PrefsTalk {
    SharedPreferences sharedPreferences;

    public PrefsTalk(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);

    }

    public void getSearch(String search) {
        sharedPreferences.edit().putString("search", search).commit();

    }

    public String getSearch() {
        return sharedPreferences.getString("search", "talks");
    }
}


