package com.nividimka.yandextranslater.model.database;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class TranslateDAO extends BaseDaoImpl<TranslateResults, Integer> {

    public TranslateDAO(ConnectionSource connectionSource,
                        Class<TranslateResults> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }


    public List<TranslateResults> getResults(String s) throws SQLException {
        QueryBuilder<TranslateResults, Integer> qb = this.queryBuilder();
        return qb.where().like("translated_to", "%"+s+"%").or().like("translated_from", "%"+s+"%").query();
    }
    public List<TranslateResults> getFaveResults(String s) throws SQLException {
        if(s.equals("")){
            return  queryForAll();
        }
        QueryBuilder<TranslateResults, Integer> qb = this.queryBuilder();
        qb.where().like("translated_to", s).or().like("translated_from", s).and().like("faved", true);
        return qb.query();
    }
}
