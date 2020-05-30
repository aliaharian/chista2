package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.Activity.search;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class main_list_cat_adapter extends RecyclerView.Adapter<main_list_cat_adapter.MyViewHolder> {

    private JSONObject list;
    private AppCompatActivity appCompat;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public ImageView img;
        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.main_lines_cat_row_name);
            img =  view.findViewById(R.id.main_lines_cat_row_img);
        }
    }

    public main_list_cat_adapter(JSONObject list,Context c) {
        this.list = list;
        context=c;
        appCompat=(AppCompatActivity)c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_cats_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        try {
            final JSONObject tmp = new JSONObject(list.getString("category" + (position + 1)));
            Picasso
                    .get()
                    .load(Params.pic_cat+tmp.getString("image"))
                    .error(R.drawable.nopic)
                    .into(holder.img);
            //holder.img.setColorFilter(R.color.Abi1);
            holder.name.setText(tmp.getString("name"));

            holder.itemView.setOnClickListener(v -> {
                try {
                    String body="{\"type\":\"filter_result\",\"q\":\"\",\"valcat\":\""+tmp.getString("id")+","+"\",\"valtag\":\"\",\"valcity\":\"\",\"range\":\"\",\"gender\":\"\",\"isonline\":\"\"}";
                    Intent i=new Intent(context, search.class);
                    i.putExtra("body",body);
                    appCompat.startActivity(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
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
