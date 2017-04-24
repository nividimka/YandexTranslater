package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;



public class TranslateResponse {
    @SerializedName("code")
    public int code;
    @SerializedName("lang")
    public String language;
    @SerializedName("text")
    public String[] text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
