package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;



public class LanguageListResponse {
    @SerializedName("dirs")
    public ArrayList<String> directions;
    @SerializedName("langs")
    public ArrayList<LanguageResponse> languages;
}
