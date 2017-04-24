package com.nividimka.yandextranslater.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nividimka.yandextranslater.R;

/**
 * Created by Alex on 24.04.2017.
 */

public class TranslateViewHolder extends RecyclerView.ViewHolder {
    ImageButton bookmark;
    TextView textFrom;
    TextView textTo;
    TextView language;
    public TranslateViewHolder(View itemView) {
        super(itemView);
        bookmark = (ImageButton) itemView.findViewById(R.id.bookmark);
        textFrom = (TextView) itemView.findViewById(R.id.text_from);
        textTo = (TextView) itemView.findViewById(R.id.text_to);
        language = (TextView) itemView.findViewById(R.id.language);
    }
}
