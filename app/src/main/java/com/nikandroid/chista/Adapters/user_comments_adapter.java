package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.fuzzproductions.ratingbar.RatingBar;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

public class user_comments_adapter extends RecyclerView.Adapter<user_comments_adapter.MyViewHolder> {

    private JSONObject list;
    private AppCompatActivity appCompat;
    private Context context;
    private Function fun;
    private PowerMenu powerMenucm;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name,text,date,likecount;
        public ImageView img,like;
        public RatingBar rating;
        public MaterialIconView menu;
        public MyViewHolder(View view) {
            super(view);
            name =  view.findViewById(R.id.comments_row_name);
            likecount =  view.findViewById(R.id.comments_row_likecount);
            text =  view.findViewById(R.id.comments_row_text);
            //date =  view.findViewById(R.id.comments_row_date);
            img =  view.findViewById(R.id.comments_row_image);
            rating =  view.findViewById(R.id.comments_row_rating);
            like =  view.findViewById(R.id.comments_row_like);
            menu =  view.findViewById(R.id.comments_row_menu);
        }
    }


    public user_comments_adapter(JSONObject list,Context c,Function f) {
        this.list = list;
        context=c;
        appCompat=(AppCompatActivity)c;
        fun=f;
    }

    @Override
    public user_comments_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comments_row, parent, false);
        return new user_comments_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final user_comments_adapter.MyViewHolder holder, final int position) {


        JSONObject tmp = null;
        try {
            tmp = new JSONObject(list.getString(position +""));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {

            holder.name.setText(tmp.getString("user_name"));
            holder.likecount.setText(tmp.getString("likes"));
            holder.text.setText(tmp.getString("text"));
            //holder.date.setText(tmp.getString("p_created_at"));
            holder.rating.setRating(Float.parseFloat(tmp.getString("star")));
           // holder.rating.

            JSONObject finalTmp = tmp;
            holder.like.setOnClickListener(v -> {

                try {
                    fun.set_like_comment(finalTmp.getString("id"),holder.like,holder.likecount);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });

            switch(tmp.getString("liked")){
                case "0":
                    holder.like.setImageResource(R.drawable.main_icon_like1);
                    break;
                case "1":
                    holder.like.setImageResource(R.drawable.main_icon_like2);
                    break;
            }

            JSONObject finalTmp1 = tmp;
            holder.menu.setOnClickListener(v -> {

                powerMenucm = new PowerMenu.Builder(context)
                        .addItem(new PowerMenuItem("گزارش نظر"))
                        .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT) // Animation start point (TOP | LEFT).
                        .setMenuRadius(10f) // sets the corner radius.
                        .setMenuShadow(10f) // sets the shadow.
                        .setTextColor(ContextCompat.getColor(context, R.color.siyah))
                        .setTextGravity(Gravity.RIGHT)
                        .setSelectedTextColor(Color.RED)
                        .setMenuColor(Color.WHITE)
                        .setSelectedMenuColor(ContextCompat.getColor(context, R.color.colorPrimary))
                        .setOnMenuItemClickListener((position1, item) -> {
                            if(position1 ==0){
                                try {
                                    fun.report_comment(finalTmp1.getString("id"));
                                    powerMenucm.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .build();
                powerMenucm.showAsDropDown(v);


            });

        }catch (Exception e){

        }

        try {
            Picasso
                    .get()
                    .load(Params.pic_adviseravatae+tmp.getString("user_avatar"))
                    .error(R.drawable.nopic)
                    .into(holder.img);
        } catch (JSONException e) {
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

