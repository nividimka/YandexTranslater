package com.nividimka.yandextranslater.ui;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.nividimka.yandextranslater.model.HelperFactory;



public class AppController extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
        Stetho.initializeWithDefaults(this);
    }
}
