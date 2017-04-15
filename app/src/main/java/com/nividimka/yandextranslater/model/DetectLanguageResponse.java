package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;



public class DetectLanguageResponse {
    @SerializedName("code")
    int code;
    @SerializedName("lang")
    public String language;
}
