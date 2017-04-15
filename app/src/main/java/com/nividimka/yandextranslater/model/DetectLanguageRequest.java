package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;



public class DetectLanguageRequest extends BaseYandexRequest {
    @SerializedName("text")
    public String text;
    @SerializedName("hint")
    public String hint;
}
