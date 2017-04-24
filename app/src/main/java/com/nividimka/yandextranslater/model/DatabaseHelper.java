package com.nividimka.yandextranslater.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nividimka.yandextranslater.model.database.Language;
import com.nividimka.yandextranslater.model.database.LanguageDAO;
import com.nividimka.yandextranslater.model.database.TranslateDAO;
import com.nividimka.yandextranslater.model.database.TranslateResults;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "translate.db";

    private static final int DATABASE_VERSION = 1;

    private LanguageDAO languageDAO = null;
    private TranslateDAO translateDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Language.class);
            TableUtils.createTable(connectionSource, TranslateResults.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
        try {
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, Language.class, true);
            TableUtils.dropTable(connectionSource, TranslateResults.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LanguageDAO getLanguageDAO(){

        if (languageDAO == null) {
            try {
                languageDAO = new LanguageDAO(getConnectionSource(), Language.class);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return languageDAO;
    }
    public TranslateDAO getTranslateResultsDAO(){

        if (translateDAO == null) {
            try {
                translateDAO = new TranslateDAO(getConnectionSource(), TranslateResults.class);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return translateDAO;
    }
    @Override
    public void close() {
        super.close();
        languageDAO = null;
        translateDAO = null;
    }
}