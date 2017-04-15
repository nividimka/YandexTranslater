package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;



public class TranslateRequest extends BaseYandexRequest{
    @SerializedName("text")
    public String text;
    @SerializedName("lang")
    public String language;
}
