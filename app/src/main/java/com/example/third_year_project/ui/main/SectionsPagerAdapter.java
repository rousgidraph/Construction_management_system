package com.example.third_year_project.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.third_year_project.R;
import com.example.third_year_project.frag_graphs;
import com.example.third_year_project.frag_inventory;
import com.example.third_year_project.frag_labourers;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
              case 0:
                Fragment frag_graphs = new frag_graphs();
                return frag_graphs;

            case 1:
                frag_inventory frag_inventory = new frag_inventory();
                return frag_inventory;

            case 2:
                frag_labourers frag_labourers = new frag_labourers();
                return frag_labourers;

            default:
                 frag_graphs = new frag_graphs();
                return frag_graphs;

        }
        //return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}