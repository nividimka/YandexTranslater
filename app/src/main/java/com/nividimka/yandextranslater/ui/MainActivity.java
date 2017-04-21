package com.nividimka.yandextranslater.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.ui.adapter.MainViewPagerAdapter;
import com.nividimka.yandextranslater.ui.fragments.BookmarkFragment;
import com.nividimka.yandextranslater.ui.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements BookmarkFragment.OnFragmentInteractionListener,SettingsFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(MainActivity.this,getSupportFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.getTabAt(0).setIcon(R.drawable.tab1_translate_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab2_bookmark_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab3_settings_icon);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
