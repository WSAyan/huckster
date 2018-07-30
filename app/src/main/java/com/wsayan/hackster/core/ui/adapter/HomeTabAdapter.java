package com.wsayan.hackster.core.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wsayan.hackster.core.R;
import com.wsayan.hackster.core.ui.fragment.NewsShelfFragment;
import com.wsayan.hackster.core.ui.fragment.NewsSourceFragment;
import com.wsayan.hackster.core.ui.fragment.TopNewsFragment;

/**
 * Created by wahid.sadique on 8/30/2017.
 */

public class HomeTabAdapter extends FragmentPagerAdapter {
    private Context context;

    public HomeTabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TopNewsFragment();
                break;
            case 1:
                fragment = new NewsSourceFragment();
                break;
            case 2:
                fragment = new NewsShelfFragment();
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.top_news_tab);
            case 1:
                return context.getString(R.string.news_tab);
            case 2:
                return context.getString(R.string.shelf_tab);
            default:
                return null;
        }
    }
}
