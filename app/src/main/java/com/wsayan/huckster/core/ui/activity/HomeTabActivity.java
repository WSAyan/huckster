package com.wsayan.huckster.core.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.wsayan.huckster.core.R;
import com.wsayan.huckster.core.presenter.AppPresenter;
import com.wsayan.huckster.core.ui.adapter.HomeTabAdapter;
import com.wsayan.huckster.core.utility.GlobalConstants;
import com.wsayan.huckster.core.utility.SharedPrefUtils;

public class HomeTabActivity extends AppCompatActivity {
    private HomeTabAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);
        initializeWidgets();
        initializeData();
        eventListeners();
    }

    private void initializeWidgets() {
        toolbar = findViewById(R.id.toolbar);
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void initializeData() {
        setSupportActionBar(toolbar);

        context = this;
        mSectionsPagerAdapter = new HomeTabAdapter(getSupportFragmentManager(), context);

        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);

        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

        sharedPreferences = new AppPresenter().getSharedPrefInterface(context);
        sharedPreferences.edit().putString(SharedPrefUtils._API_KEY, GlobalConstants.API_KEY).apply();
        sharedPreferences.edit().putString(SharedPrefUtils._LANGUAGE, GlobalConstants.ENGLISH).apply();
    }

    private void eventListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_country:
                        showListDialog(new ArrayAdapter<>(
                                context,
                                android.R.layout.simple_list_item_single_choice,
                                context.getResources().getStringArray(R.array.pref_country_entries)
                        ));
                        return true;
                    case R.id.bottom_navigation_language:
                        showListDialog(new ArrayAdapter<>(
                                context,
                                android.R.layout.simple_list_item_single_choice,
                                context.getResources().getStringArray(R.array.pref_language_entries)
                        ));
                        return true;
                    case R.id.bottom_navigation_category:
                        return true;
                }
                return false;
            }
        });
    }

    private void showListDialog(ArrayAdapter<String> arrayAdapter){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        dialogBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeTabActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
