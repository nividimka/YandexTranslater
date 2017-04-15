package com.nividimka.yandextranslater.networking;

import com.nividimka.yandextranslater.contants.UrlConstants;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.POST;

public interface YandexTranslateService {

    @POST(UrlConstants.LANG_LIST)
    Call<JSONObject> getLanguageList();

    @POST(UrlConstants.LANG_DETECT)
    Call<JSONObject> detectLanguage();

    @POST(UrlConstants.LANG_TRANSLATE)
    Call<JSONObject> translate();

}