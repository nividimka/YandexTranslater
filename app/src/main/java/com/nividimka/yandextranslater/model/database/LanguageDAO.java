package com.nividimka.yandextranslater.model.database;


import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;

public class LanguageDAO extends BaseDaoImpl<Language, Integer> {

    public LanguageDAO(ConnectionSource connectionSource,
                       Class<Language> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public HashMap<String, String> getLanguages() {
        HashMap<String, String> langs = new HashMap<>();
        try {
            List<Language> languages = this.query(this.queryBuilder().prepare());
            for (int i = 0; i < languages.size(); i++) {
                langs.put(languages.get(i).getLanguageCode(), languages.get(i).getLanguage());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return langs;
    }

    public void saveLanguages(List<Language> languages) throws SQLException {

        Log.e("asd", "saveBulkData()");
        long t1 = System.nanoTime();
        DatabaseConnection conn = startThreadConnection();
        Savepoint savepoint = null;
        try {
            savepoint = conn.setSavePoint(null);
            doInsert(languages, this);
        } finally {
            conn.commit(savepoint);
            endThreadConnection(conn);
        }
        long t2 = System.nanoTime();
        Log.e("Total time to insert", String.valueOf((t2 - t1) * 1e-9));
    }


    private void doInsert(List<Language> objectTypes, Dao<Language, Integer> simpleDao) {
        Log.d("asd","doInsert()");
        for (Language a : objectTypes) {
            try {
                if (a != null)
                    createOrUpdate(a);
            } catch (SQLException e) {
                e.printStackTrace();
                Log.e("asd","unable to save or update ={}");
            }
        }
    }
}
