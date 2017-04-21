package com.nividimka.yandextranslater.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;


public class LanguageListResponse {
    @SerializedName("dirs")
    public ArrayList<String> directions;
    @SerializedName("langs")
    public HashMap<String,String> languages;
}
