package com.wsayan.huckster.core.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wsayan.huckster.core.R;
import com.wsayan.huckster.core.presenter.AppPresenter;
import com.wsayan.huckster.core.utility.GlobalConstants;
import com.wsayan.huckster.core.utility.SharedPrefUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = new AppPresenter().getSharedPrefInterface(this);

        sharedPreferences.edit().putString(SharedPrefUtils._API_KEY, GlobalConstants.API_KEY).apply();
        if (sharedPreferences.getString(SharedPrefUtils._COUNTRY, "").isEmpty()) {
            sharedPreferences.edit().putString(SharedPrefUtils._LANGUAGE, GlobalConstants.ENGLISH).apply();
            sharedPreferences.edit().putString(SharedPrefUtils._COUNTRY, getResources().getStringArray(R.array.pref_country_values)[0]).apply();
            sharedPreferences.edit().putString(SharedPrefUtils._CATEGORY, getResources().getStringArray(R.array.pref_category_values)[0]).apply();
        }

        Intent intent = new Intent(MainActivity.this, HomeTabActivity.class);
        startActivity(intent);
    }
}
