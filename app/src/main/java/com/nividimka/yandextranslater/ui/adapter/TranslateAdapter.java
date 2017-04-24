package com.nividimka.yandextranslater.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nividimka.yandextranslater.R;
import com.nividimka.yandextranslater.model.HelperFactory;
import com.nividimka.yandextranslater.model.database.TranslateResults;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alex on 24.04.2017.
 */
//ресайклер потому-что все элементы не помещаются на экране и использовать его целесообразней чем листвью
public class TranslateAdapter extends RecyclerView.Adapter<TranslateViewHolder> {
    List<TranslateResults> results;
    Context context;

    public TranslateAdapter(Context context, List<TranslateResults> results) {
        this.results = results;
        this.context = context;
    }
    @Override
    public TranslateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.translate_item, parent, false);
        return new TranslateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TranslateViewHolder holder, int position) {
        final TranslateResults currentItem = results.get(position);
        holder.textFrom.setText(currentItem.getTranslatedFrom());
        holder.textTo.setText(currentItem.getTranslatedTo());
        holder.language.setText(currentItem.getTranslatedLangs());
        if (currentItem.isFaved()) {
            holder.bookmark.setImageResource(R.drawable.bookmark_true);
            holder.bookmark.setColorFilter(ContextCompat.getColor(context, android.R.color.black));
        } else {
            holder.bookmark.setImageResource(R.drawable.bookmark_false);
            holder.bookmark.setColorFilter(ContextCompat.getColor(context, R.color.inactive_image));
        }
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentItem.isFaved()) {
                    currentItem.setFaved(false);
                    try {
                        HelperFactory.getHelper().getTranslateResultsDAO().update(currentItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    holder.bookmark.setImageResource(R.drawable.bookmark_false);
                    holder.bookmark.setColorFilter(ContextCompat.getColor(context, R.color.inactive_image));
                } else {
                    currentItem.setFaved(true);
                    try {
                        HelperFactory.getHelper().getTranslateResultsDAO().update(currentItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    holder.bookmark.setImageResource(R.drawable.bookmark_true);
                    holder.bookmark.setColorFilter(ContextCompat.getColor(context, android.R.color.black));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
