package com.nividimka.yandextranslater.ui.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nividimka.yandextranslater.ui.fragments.BookmarkFragment;
import com.nividimka.yandextranslater.ui.fragments.SettingsFragment;
import com.nividimka.yandextranslater.ui.fragments.TranslateFragment;

//Адаптер для прокручивания списков
public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TranslateFragment();
            case 1:
                return new BookmarkFragment();
            case 2:
                return new SettingsFragment();
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return 3;
    }
}