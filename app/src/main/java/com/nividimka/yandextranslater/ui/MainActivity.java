package com.nividimka.yandextranslater.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.ui.adapter.MainViewPagerAdapter;
import com.nividimka.yandextranslater.ui.fragments.SettingsFragment;
/*
    Главный экран, с которым работает это приложение
    Включает в себя viewPager и табы для работы с фрагментами
 */
public class MainActivity extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //Не нашел лучшего способо чем задание tabIcon таким способом, хотелось бы конечно это сделать через viewPager
        tabLayout.getTabAt(0).setIcon(R.drawable.tab1_translate_icon);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab2_bookmark_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab3_settings_icon);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
