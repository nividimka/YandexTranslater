package com.nividimka.yandextranslater.networking;

import com.nividimka.yandextranslater.contants.UrlConstants;
import com.nividimka.yandextranslater.model.LanguageListResponse;
import com.nividimka.yandextranslater.model.TranslateResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface YandexTranslateService {

    @POST(UrlConstants.LANG_LIST)
    Call<LanguageListResponse> getLanguageList(@Query("key") String key);

    @POST(UrlConstants.LANG_TRANSLATE)
    Call<TranslateResponse> translate(@Query("key") String key, @Query("lang") String language,@Query("text") String text);


}