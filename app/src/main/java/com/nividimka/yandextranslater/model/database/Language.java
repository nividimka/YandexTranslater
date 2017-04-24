package com.nividimka.yandextranslater.model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "languages")
public class Language {
    @DatabaseField(id = true,columnName = "lang_code")
    private String languageCode;
    
    @DatabaseField()
    private String language;
    Language() {
    }

    public Language(String languageCode, String language) {
        this.languageCode = languageCode;
        this.language = language;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}