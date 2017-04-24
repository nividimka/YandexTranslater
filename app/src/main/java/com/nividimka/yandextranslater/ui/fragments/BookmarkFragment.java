package com.nividimka.yandextranslater.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.model.HelperFactory;
import com.nividimka.yandextranslater.model.database.TranslateResults;
import com.nividimka.yandextranslater.ui.adapter.TranslateAdapter;

import java.sql.SQLException;
import java.util.List;
/*
    Фрагментт с историей и закладками, не до конца доработанный
 */
public class BookmarkFragment extends Fragment {

    public BookmarkFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    TranslateAdapter translateAdapter;
    List<TranslateResults> results;
    EditText filter;
    TextView favorite;
    TextView history;
    boolean faved = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        history = (TextView) view.findViewById(R.id.history);
        favorite = (TextView) view.findViewById(R.id.favorite);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setHistory();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFavorite();
            }
        });
        filter = (EditText) view.findViewById(R.id.filter);
        try {
            //Знаюю что нужно работать с бд в потоке
            //Таски лоадеры хэндлеры и пр. Для доступа к ормке выбрал бы лоадер
            if(faved){
                results = HelperFactory.getHelper().getTranslateResultsDAO().getFaveResults(filter.getText().toString());
            }else {
                results = HelperFactory.getHelper().getTranslateResultsDAO().getResults(filter.getText().toString());
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        translateAdapter = new TranslateAdapter(getContext(), results);
        recyclerView.setAdapter(translateAdapter);
        //При вводе текста обновляем результаты выборки
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResults();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setHistory();
        return view;
    }

    private void setFavorite() {
        favorite.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));
        history.setTextColor(ContextCompat.getColor(getContext(),R.color.inactive_image));
        faved = true;
    }

    private void setHistory(){
        favorite.setTextColor(ContextCompat.getColor(getContext(),R.color.inactive_image));
        history.setTextColor(ContextCompat.getColor(getContext(),android.R.color.black));
        faved = false;
    }

    private void updateResults(){
        try {
            results.clear();
            if(faved){
                results.addAll(HelperFactory.getHelper().getTranslateResultsDAO().getFaveResults(filter.getText().toString()));
            }else{
                results.addAll(HelperFactory.getHelper().getTranslateResultsDAO().getResults(filter.getText().toString()));
            }
            translateAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
