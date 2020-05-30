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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.Activity.user;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

public class my_adviser_adapter extends RecyclerView.Adapter<my_adviser_adapter.MyViewHolder> {

    private JSONObject list;
    private Function fun;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,des,hr,loc,price,star;
        MaterialIconView bookmark;
        public ImageView img,ost;
        public CardView maincard;
        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.myadviser_row_name);
            des =  view.findViewById(R.id.myadviser_row_des);
            hr =  view.findViewById(R.id.myadviser_row_hr);
            loc =  view.findViewById(R.id.myadviser_row_loc);
            price =  view.findViewById(R.id.myadviser_row_price);
            img =  view.findViewById(R.id.myadviser_row_img);
            ost =  view.findViewById(R.id.myadviser_row_onlinestatus);
            star =  view.findViewById(R.id.myadviser_row_star);
            bookmark =  view.findViewById(R.id.myadviser_row_bookmark);

        }
    }


    public my_adviser_adapter(JSONObject list,Context c,Function f) {
        this.list = list;
        context=c;
        fun=f;
    }

    @Override
    public my_adviser_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_adviser_row, parent, false);
        return new my_adviser_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final my_adviser_adapter.MyViewHolder holder, final int position) {


        try {
            final JSONObject tmp = new JSONObject(list.getString((position )+""));

            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+tmp.getString("avatar"))
                    .error(R.drawable.nopic)
                    .into(holder.img);

            holder.name.setText(tmp.getString("name"));
            //holder.des.setText(tmp.getString("short_desc"));
            holder.loc.setText(tmp.getString("city"));
            holder.hr.setText(tmp.getString("chat_time")+"دقیقه");
            //holder.price.setText(tmp.getString("price"));
           // holder.star.setText(tmp.getString("star"));

            if(tmp.getString("is_online").equals("2")){
                holder.ost.setImageResource(R.drawable.z_circle_busy);
            }else if(tmp.getString("is_online").equals("1")){
                holder.ost.setImageResource(R.drawable.z_circle_online);
            }else{
                holder.ost.setImageResource(R.drawable.z_circle_offline);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i=new Intent(context, user.class);
                        i.putExtra("adid",tmp.getString("id"));
                        ((AppCompatActivity)context).startActivity(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.bookmark.setOnClickListener(v -> {

                try {
                    fun.set_mark_server(holder.bookmark,tmp.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        }catch (Exception e){
            e.printStackTrace();
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
