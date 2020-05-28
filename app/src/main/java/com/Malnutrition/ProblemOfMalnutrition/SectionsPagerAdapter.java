package com.Malnutrition.ProblemOfMalnutrition;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:
                Fragment_Tabbed_Breakfast tab1 = new Fragment_Tabbed_Breakfast();
                return tab1;
            case 1:
                Fragment_Tabbed_Lunch tab2=new Fragment_Tabbed_Lunch();
                return  tab2;
            case 2:
                Fragment_Tabbed_Dinner tab3=new Fragment_Tabbed_Dinner();
                return  tab3;

                default:
            return null;

        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Breakfast";
            case 1:
                return "Launch";
            case 2:
                return "Dinner";

            default:
                return null;
        }
    }

}