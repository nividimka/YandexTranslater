package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;



public class TranslateResponse {
    @SerializedName("code")
    public int code;
    @SerializedName("lang")
    public String language;
    @SerializedName("text")
    public String text;
}
