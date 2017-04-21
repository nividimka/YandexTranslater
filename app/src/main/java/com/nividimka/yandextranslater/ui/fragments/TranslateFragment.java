package com.nividimka.yandextranslater.ui.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.model.LanguageListResponse;
import com.nividimka.yandextranslater.networking.ApiFactory;
import com.nividimka.yandextranslater.networking.YandexTranslateService;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

public class TranslateFragment extends Fragment {

    private Timer timer = new Timer();
    private final long DELAY = 500;


    public TranslateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment1, container, false);
        ImageButton clearText = (ImageButton) view.findViewById(R.id.clear_text);
        final EditText inputText = (EditText) view.findViewById(R.id.input_text);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timer.cancel();
//                showLoading();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {

                                    public void run() {
//                                        page = 1;
//                                        cases.clear();
                                        translate("ru-en","google");
                                    }
                                });
                            }
                        },
                        DELAY
                );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText("");
                Toast.makeText(getContext(), "ASD", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void translate(final String language, final String text) {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                YandexTranslateService yandexTranslateService = ApiFactory.getTranslateService();
                try {
                    Response<LanguageListResponse> response = yandexTranslateService.getLanguageList().execute();
                    for (Map.Entry<String, String> entry : response.body().languages.entrySet())
                    {
                        Log.e(entry.getKey(), entry.getValue());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }



}
