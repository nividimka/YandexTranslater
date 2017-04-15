package com.nividimka.yandextranslater.networking;

import com.nividimka.yandextranslater.contants.UrlConstants;
import com.nividimka.yandextranslater.model.DetectLanguageRequest;
import com.nividimka.yandextranslater.model.DetectLanguageResponse;
import com.nividimka.yandextranslater.model.LanguageListRequest;
import com.nividimka.yandextranslater.model.LanguageListResponse;
import com.nividimka.yandextranslater.model.TranslateRequest;
import com.nividimka.yandextranslater.model.TranslateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface YandexTranslateService {

    @POST(UrlConstants.LANG_LIST)
    Call<LanguageListResponse> getLanguageList(@Body LanguageListRequest request);

    @POST(UrlConstants.LANG_DETECT)
    Call<DetectLanguageResponse> detectLanguage(@Body DetectLanguageRequest request);

    @POST(UrlConstants.LANG_TRANSLATE)
    Call<TranslateResponse> translate(@Body TranslateRequest request);

}