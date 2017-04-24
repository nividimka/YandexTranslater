package com.nividimka.yandextranslater.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        try {
            results  = HelperFactory.getHelper().getTranslateResultsDAO().getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        translateAdapter = new TranslateAdapter(getContext(),results);
        recyclerView.setAdapter(translateAdapter);
        return view;
    }
}
