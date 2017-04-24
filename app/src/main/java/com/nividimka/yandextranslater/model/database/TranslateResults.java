package com.nividimka.yandextranslater.model.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "translated_results")
public class TranslateResults {
    @DatabaseField(generatedId = true,columnName = "_id")
    private int id;
    @DatabaseField(columnName = "translated_from")
    private String translatedFrom;
    @DatabaseField(columnName ="translated_to")
    private String translatedTo;
    @DatabaseField(columnName = "translated_langs")
    private String translatedLangs;
    @DatabaseField(columnName = "faved")
    private boolean faved;

    public boolean isFaved() {
        return faved;
    }

    public void setFaved(boolean faved) {
        this.faved = faved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTranslatedFrom() {
        return translatedFrom;
    }

    public void setTranslatedFrom(String translatedFrom) {
        this.translatedFrom = translatedFrom;
    }

    public String getTranslatedTo() {
        return translatedTo;
    }

    public void setTranslatedTo(String translatedTo) {
        this.translatedTo = translatedTo;
    }

    public String getTranslatedLangs() {
        return translatedLangs;
    }

    public void setTranslatedLangs(String translatedLangs) {
        this.translatedLangs = translatedLangs;
    }
}
