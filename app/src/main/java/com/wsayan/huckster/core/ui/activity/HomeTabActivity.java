package com.wsayan.huckster.core.ui.activity;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wsayan.huckster.core.R;
import com.wsayan.huckster.core.presenter.AppPresenter;
import com.wsayan.huckster.core.ui.adapter.HomeTabAdapter;
import com.wsayan.huckster.core.ui.adapter.SelectListAdapter;
import com.wsayan.huckster.core.utility.CommonOperations;
import com.wsayan.huckster.core.utility.GlobalConstants;
import com.wsayan.huckster.core.utility.SharedPrefUtils;

public class HomeTabActivity extends AppCompatActivity {
    private HomeTabAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Context context;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ImageView toolbarImageView;
    private SharedPreferences sharedPreferences;
    //private BottomNavigationView bottomNavigationView;
    private int selectedCatIndex, selectedCountryIndex;

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
        //toolbarImageView = findViewById(R.id.toolbar_imageView);
        //bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    private void initializeData() {
        context = this;
        setSupportActionBar(toolbar);
        sharedPreferences = new AppPresenter().getSharedPrefInterface(context);
        selectedCatIndex = CommonOperations.getSharedPrefListPosition(getResources().getStringArray(R.array.pref_category_values), sharedPreferences.getString(SharedPrefUtils._CATEGORY, ""));
        selectedCountryIndex = CommonOperations.getSharedPrefListPosition(getResources().getStringArray(R.array.pref_country_values), sharedPreferences.getString(SharedPrefUtils._COUNTRY, ""));

        getSupportActionBar().setTitle(" " + getResources().getStringArray(R.array.pref_category_entries)[selectedCatIndex]);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setLogo(context.getTheme().getDrawable(GlobalConstants.COUNTRY_ICONS[selectedCountryIndex]));
        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbarImageView.destroyDrawingCache();
            toolbarImageView.setImageDrawable(context.getTheme().getDrawable(GlobalConstants.COUNTRY_ICONS[selectedCountryIndex]));
        }*/

        mSectionsPagerAdapter = new HomeTabAdapter(getSupportFragmentManager(), context);

        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);

        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void eventListeners() {
        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_navigation_country:
                        showListDialog(context.getResources().getStringArray(R.array.pref_country_entries));
                        return true;
                    case R.id.bottom_navigation_language:
                        showListDialog(context.getResources().getStringArray(R.array.pref_language_entries));
                        return true;
                    case R.id.bottom_navigation_category:
                        return true;
                }
                return false;
            }
        });*/
    }

    private void showListDialog(String[] selectArray) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_select);
        dialog.setTitle("SELECT");


        SelectListAdapter selectListAdapter = new SelectListAdapter(context, selectArray);
        RecyclerView selectRecyclerView = dialog.findViewById(R.id.selectRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        selectRecyclerView.setLayoutManager(layoutManager);
        selectRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectRecyclerView.setAdapter(selectListAdapter);
        selectListAdapter.notifyDataSetChanged();

        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_tabbed, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeTabActivity.this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_category) {
            showCategoryListDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showCategoryListDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog);
        alertDialog.setTitle(context.getString(R.string.pref_settings_title_category));
        selectedCatIndex = CommonOperations.getSharedPrefListPosition(getResources().getStringArray(R.array.pref_category_values), sharedPreferences.getString(SharedPrefUtils._CATEGORY, ""));
        alertDialog.setSingleChoiceItems(getResources().getStringArray(R.array.pref_category_entries), selectedCatIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedCatIndex = i;
            }
        });

        alertDialog.setPositiveButton(context.getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences.edit().putString(SharedPrefUtils._CATEGORY, getResources().getStringArray(R.array.pref_category_values)[selectedCatIndex]).apply();
                initializeData();
            }
        });

        alertDialog.setNegativeButton(context.getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializeData();
    }
}
