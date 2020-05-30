package com.nikandroid.chista.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.R;

import org.json.JSONObject;

public class list_tags_adapter extends RecyclerView.Adapter<list_tags_adapter.MyViewHolder> {

    private JSONObject list;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public MyViewHolder(View view) {
            super(view);
            name =  itemView.findViewById(R.id.row_tags_name);
        }
    }


    public list_tags_adapter(JSONObject list) {
        this.list = list;
    }

    @Override
    public list_tags_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_tags, parent, false);
        return new list_tags_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final list_tags_adapter.MyViewHolder holder, final int position) {


        try {
            holder.name.setText(list.getString(position+""));

        }catch (Exception e){
        }


    }

    @Override
    public int getItemCount() {
        return list.length();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
