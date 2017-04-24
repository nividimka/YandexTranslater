package com.nividimka.yandextranslater.ui;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.nividimka.yandextranslater.model.HelperFactory;



public class AppController extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //база данных
        HelperFactory.setHelper(getApplicationContext());
        //испектинг бдшки
        Stetho.initializeWithDefaults(this);
    }
}
