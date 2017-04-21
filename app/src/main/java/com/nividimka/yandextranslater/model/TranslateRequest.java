package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;



public class TranslateRequest extends BaseYandexRequest{
    @SerializedName("text")
    public String text;
    @SerializedName("lang")
    public String language;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
