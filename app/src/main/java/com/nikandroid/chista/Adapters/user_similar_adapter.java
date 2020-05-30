package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.Activity.media_show;
import com.nikandroid.chista.Activity.user;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class user_similar_adapter extends RecyclerView.Adapter<user_similar_adapter.MyViewHolder> {

    private JSONObject list;
    public String fullgalres="";
    private AppCompatActivity appCompat;
    private Context context;
    private Function fun;
    LinearLayout.LayoutParams ilp,vlp;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,des,price;
        public ImageView img,ost;
        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.main_line_adviserbox2_row_name);
            des =  view.findViewById(R.id.main_line_adviserbox2_row_des);
            price =  view.findViewById(R.id.main_line_adviserbox2_row_price);
            img =  view.findViewById(R.id.main_line_adviserbox2_row_img);
            ost =  view.findViewById(R.id.main_line_adviserbox2_row_onlinestatus);

        }
    }


    public user_similar_adapter(JSONObject list, String res, Context c, Function f) {
        this.list = list;
        this.fullgalres=res;
        context=c;
        appCompat=(AppCompatActivity)c;
        fun=f;
    }

    @Override
    public user_similar_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_line_adviser2_row, parent, false);
        return new user_similar_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final user_similar_adapter.MyViewHolder holder, final int position) {

        try {
            final JSONObject tmp = new JSONObject(list.getString("" + position));

            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+tmp.getString("avatar"))
                    .error(R.drawable.nopic)
                    .into(holder.img);

            holder.name.setText(tmp.getString("name"));
            holder.des.setText(tmp.getString("field"));
            holder.price.setText(fun.change_adad(fun.farsi_sazi_adad(tmp.getString("price")))+" تومان");


            if(tmp.getString("is_online").equals("2")){
                holder.ost.setImageResource(R.drawable.z_circle_busy);
            }else if(tmp.getString("is_online").equals("1")){
                holder.ost.setImageResource(R.drawable.z_circle_online);
            }else{
                holder.ost.setImageResource(R.drawable.z_circle_offline);
            }
            //holder.ost.setVisibility(View.GONE);

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
