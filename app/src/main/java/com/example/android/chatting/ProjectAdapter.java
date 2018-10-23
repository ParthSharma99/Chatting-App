package com.example.android.chatting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProjectAdapter extends FragmentPagerAdapter {

    int tabs;

    public ProjectAdapter(FragmentManager fm, int NumberOfTabs){
        super(fm);
        this.tabs = NumberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Left";
            case 1:
                return "Middle";
            case 2:
                return "Right";
        }
        return super.getPageTitle(position);
    }
}
