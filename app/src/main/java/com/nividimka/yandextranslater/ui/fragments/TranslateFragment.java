package com.nividimka.yandextranslater.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.contants.UrlConstants;
import com.nividimka.yandextranslater.model.LanguageListResponse;
import com.nividimka.yandextranslater.model.TranslateResponse;
import com.nividimka.yandextranslater.model.database.Language;
import com.nividimka.yandextranslater.model.database.TranslateResults;
import com.nividimka.yandextranslater.networking.ApiFactory;
import com.nividimka.yandextranslater.model.HelperFactory;
import com.nividimka.yandextranslater.networking.YandexTranslateService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class TranslateFragment extends Fragment {
    LinkedHashMap<String, String> languageMap;
    private Timer timer = new Timer();
    private final long DELAY = 500;
    TextView languageFrom;
    TextView languageTo;
    SharedPreferences sPref;
    ImageButton bookmark;
    TranslateResults tr;

    final String LANG_FROM_CODE = "language_from";
    final String LANG_TO_CODE = "language_to";


    public TranslateFragment() {
        // Required empty public constructor
    }

    AsyncTask<Void,Void,Void> request;
    EditText inputText;
    TextView outputText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment1, container, false);
        ImageButton clearText = (ImageButton) view.findViewById(R.id.clear_text);
        inputText = (EditText) view.findViewById(R.id.input_text);
        outputText = (TextView) view.findViewById(R.id.output_text);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        final ImageView swap = (ImageView) view.findViewById(R.id.swap);
        swap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String swapFrom = loadFromTag();
                String swapTo = loadToTag();
                languageFrom.setText(languageMap.get(swapTo));
                languageTo.setText(languageMap.get(swapFrom));
                saveFromTag(swapTo);
                saveToTag(swapFrom);
                translate();
            }
        });
        swap.setVisibility(View.GONE);
        languageFrom = (TextView) view.findViewById(R.id.language_from);
        languageTo = (TextView) view.findViewById(R.id.language_to);
        languageFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Выберете язык");
                final String[] keys = languageMap.keySet().toArray(new String[0]);
                final String[] values = languageMap.values().toArray(new String[0]);
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //Если ставят противоположный язык, то меняем местами
                        //Если другой язык, то просто оставляем этот язык
                        if (keys[item].equals(loadToTag())) {
                            languageTo.setText(languageMap.get(loadFromTag()));
                            languageFrom.setText(values[item]);
                            saveToTag(loadFromTag());
                            saveFromTag(keys[item]);
                        } else {
                            saveFromTag(keys[item]);
                            languageFrom.setText(values[item]);
                        }
                        translate();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        languageTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Выберете язык");
                final String[] keys = languageMap.keySet().toArray(new String[0]);
                final String[] values = languageMap.values().toArray(new String[0]);
                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //Если ставят противоположный язык, то меняем местами
                        if (keys[item].equals(loadFromTag())) {
                            languageFrom.setText(languageMap.get(loadToTag()));
                            languageTo.setText(values[item]);
                            saveFromTag(loadToTag());
                            saveToTag(keys[item]);
                        } else {
                            saveToTag(keys[item]);
                            languageTo.setText(values[item]);
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                    }
                });
                translate();
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        bookmark = (ImageButton) view.findViewById(R.id.bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tr!=null){
                    if (tr.isFaved()) {
                        tr.setFaved(false);
                        try {
                            HelperFactory.getHelper().getTranslateResultsDAO().update(tr);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        bookmark.setImageResource(R.drawable.bookmark_false);
                        bookmark.setColorFilter(ContextCompat.getColor(getContext(),R.color.inactive_image));
                    } else {
                        tr.setFaved(true);
                        try {
                            HelperFactory.getHelper().getTranslateResultsDAO().update(tr);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        bookmark.setImageResource(R.drawable.bookmark_true);
                        bookmark.setColorFilter(ContextCompat.getColor(getContext(),android.R.color.black));
                    }
                }
            }
        });
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    if (checkInternetConnection()) {
                        YandexTranslateService yandexTranslateService = ApiFactory.getTranslateService();
                        Response<LanguageListResponse> response = yandexTranslateService.getLanguageList(UrlConstants.API_KEY).execute();
                        if (response.code() == 200) {
                            ArrayList<Language> languages = new ArrayList<>();
                            languageMap = sortHashMapByValues(response.body().languages);
                            for (Map.Entry<String, String> entry : languageMap.entrySet()) {
                                languages.add(new Language(entry.getKey(), entry.getValue()));
                            }
                            HelperFactory.getHelper().getLanguageDAO().saveLanguages(languages);
                        } else {
                            languageMap = sortHashMapByValues(HelperFactory.getHelper().getLanguageDAO().getLanguages());
                        }
                    } else {
                        languageMap = sortHashMapByValues(HelperFactory.getHelper().getLanguageDAO().getLanguages());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (languageMap != null) {
                    languageFrom.setText(languageMap.get(loadFromTag()));
                    languageTo.setText(languageMap.get(loadToTag()));
                    swap.setVisibility(View.VISIBLE);
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
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
                                        translate();
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
            }
        });
        return view;
    }

    public LinkedHashMap<String, String> sortHashMapByValues(
            HashMap<String, String> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<String> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        LinkedHashMap<String, String> sortedMap =
                new LinkedHashMap<>();

        Iterator<String> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            String val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                String comp1 = passedMap.get(key);
                String comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    private void translate() {
        final String language = loadFromTag() + "-" + loadToTag();
        final String text = inputText.getText().toString();
        if(request!=null){
            request.cancel(true);
        }
        request = new AsyncTask<Void, Void, Void>() {
            String outputString="";

            @Override
            protected Void doInBackground(Void... voids) {
                YandexTranslateService yandexTranslateService = ApiFactory.getTranslateService();
                try {
                    if (checkInternetConnection()) {
                        Response<TranslateResponse> response = yandexTranslateService.translate(UrlConstants.API_KEY, language, text).execute();
                        if (response.code() == 200) {
                            String[] array = response.body().getText();
                                if(array.length>=1) {
                                    outputString = array[0];
                                }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                outputText.setText(outputString);
                if (!outputString.equals("")) {
                    tr = new TranslateResults();
                    tr.setTranslatedFrom(text);
                    tr.setTranslatedTo(outputString);
                    tr.setTranslatedLangs(language);
                    tr.setFaved(false);
                    bookmark.setImageResource(R.drawable.bookmark_false);
                    bookmark.setColorFilter(ContextCompat.getColor(getContext(),R.color.inactive_image));
                    try {
                        HelperFactory.getHelper().getTranslateResultsDAO().create(tr);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    void saveFromTag(String text) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(LANG_FROM_CODE, text);
        ed.apply();
    }

    String loadFromTag() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        return sPref.getString(LANG_FROM_CODE, "ru");
    }

    void saveToTag(String text) {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(LANG_TO_CODE, text);
        ed.apply();
    }

    String loadToTag() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        return sPref.getString(LANG_TO_CODE, "en");
    }

    private boolean checkInternetConnection() {

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        return networkInfo != null && networkInfo.isConnected();
    }
}
