package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.Activity.user;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class main_list_adviser_horezontal_adapter extends RecyclerView.Adapter<main_list_adviser_horezontal_adapter.MyViewHolder> {

    private JSONObject list;
    private AppCompatActivity appCompat;
    private Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,des;
        public ImageView img,ost;
        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.main_line_adviserbox2_row_name);
            des =  view.findViewById(R.id.main_line_adviserbox2_row_des);
            img =  view.findViewById(R.id.main_line_adviserbox2_row_img);
            ost =  view.findViewById(R.id.main_line_adviserbox2_row_onlinestatus);
        }
    }


    public main_list_adviser_horezontal_adapter(JSONObject list,Context c) {
        this.list = list;
        context=c;
        appCompat=(AppCompatActivity)c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_adviser2_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        try {
            final JSONObject tmp = new JSONObject(list.getString("adviser" + (position + 1)));
            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+tmp.getString("avatar"))
                    .error(R.drawable.nopic)
                    .into(holder.img);

            holder.name.setText(tmp.getString("name"));
            holder.des.setText(tmp.getString("short_desc"));

            if(tmp.getString("is_online").equals("2")){
                holder.ost.setImageResource(R.drawable.z_circle_busy);
            }else if(tmp.getString("is_online").equals("1")){
                holder.ost.setImageResource(R.drawable.z_circle_online);
            }else{
                holder.ost.setImageResource(R.drawable.z_circle_offline);
            }

            holder.itemView.setOnClickListener(v -> {
                try {
                    Intent i=new Intent(context, user.class);
                    i.putExtra("adid",tmp.getString("id"));
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

