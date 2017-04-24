package com.nividimka.yandextranslater.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.model.HelperFactory;
import com.nividimka.yandextranslater.model.database.TranslateResults;
import com.nividimka.yandextranslater.ui.adapter.TranslateAdapter;

import java.sql.SQLException;
import java.util.List;

public class BookmarkFragment extends Fragment {

    public BookmarkFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerView;
    TranslateAdapter translateAdapter;
    List<TranslateResults> results;
    EditText filter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        filter = (EditText) view.findViewById(R.id.filter);
        try {
            results = HelperFactory.getHelper().getTranslateResultsDAO().getResults(filter.getText().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        translateAdapter = new TranslateAdapter(getContext(), results);
        recyclerView.setAdapter(translateAdapter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    results = HelperFactory.getHelper().getTranslateResultsDAO().getResults(filter.getText().toString());
                    translateAdapter.updateResults(results);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
}
