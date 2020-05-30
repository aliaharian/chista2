package com.nikandroid.chista.Utils;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.R;

class StringChooserView extends RecyclerView.ViewHolder {

    TextView title_view;

    StringChooserView(@NonNull View itemView) {
        super(itemView);
        title_view = itemView.findViewById(R.id.Item18_title);
        if (title_view != null) {

        }
    }
}
