package com.nividimka.yandextranslater.model.database;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class TranslateDAO extends BaseDaoImpl<TranslateResults, Integer> {

    public TranslateDAO(ConnectionSource connectionSource,
                       Class<TranslateResults> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public List<TranslateResults> getResults() throws SQLException {
        return this.queryForAll();
    }
}
