package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nikandroid.chista.Activity.media_show;
import com.nikandroid.chista.Activity.user;
import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONException;
import org.json.JSONObject;

public class user_gallery_adapter extends RecyclerView.Adapter<user_gallery_adapter.MyViewHolder> {

    private JSONObject list;
    public String fullgalres="";
    private AppCompatActivity appCompat;
    private Context context;
    private Function fun;
    LinearLayout.LayoutParams ilp,vlp;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public ImageView vmark;
        public CardView card;
        public View l1;
        public MyViewHolder(View view) {
            super(view);
            img =  view.findViewById(R.id.user_gallery_row_img);
            vmark =  view.findViewById(R.id.user_gallery_row_videomark);
            card =  view.findViewById(R.id.user_gallery_row_card);
            l1 =  view.findViewById(R.id.user_gallery_row_l1);
        }
    }


    public user_gallery_adapter(JSONObject list,String res,Context c,Function f) {
        this.list = list;
        this.fullgalres=res;
        context=c;
        appCompat=(AppCompatActivity)c;
        fun=f;
        ilp=new LinearLayout.LayoutParams(310,310);
        vlp=new LinearLayout.LayoutParams(440,310);


    }

    @Override
    public user_gallery_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_gallery_row, parent, false);
        return new user_gallery_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final user_gallery_adapter.MyViewHolder holder, final int position) {


        try {
            final JSONObject tmp = new JSONObject(list.getString(position +""));

            switch (tmp.getString("type")){
                case "video":
                    //
                    holder.card.setLayoutParams(vlp);
                    if(user.getcovetvideo){
                        try{
                            holder.img.setImageBitmap(user.videocover);
                        }catch (Exception e){
                        }
                    }else{
                        new Thread(() -> {
                            try {
                                fun.retriveVideoFrameFromVideo(tmp.getString("file_path"),holder.img);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                    holder.card.setOnClickListener(v -> {
                        Intent i=new Intent(context, media_show.class);
                        i.putExtra("slider",fullgalres);
                        i.putExtra("cp",position);
                        appCompat.startActivity(i);
                    });


                    break;

                case "image":
                    holder.vmark.setVisibility(View.GONE);
                    holder.l1.setVisibility(View.GONE);
                    holder.card.setLayoutParams(ilp);
                    if(!tmp.getString("file_path").equals("end")){
                        Picasso.get()
                                .load(tmp.getString("file_path"))
                                .placeholder(R.drawable.blank_pic)
                                .error(R.drawable.main_nopic_gallery)
                                .into(holder.img);
                    }else{
                        Picasso.get()
                                .load(R.drawable.main_nopic_gallery)
                                .into(holder.img);
                    }
                    Picasso.get()
                            .load(tmp.getString("file_path"))
                            .placeholder(R.drawable.blank_pic)
                            .error(R.drawable.main_nopic_gallery)
                            .into(holder.img, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            //holder.itemView.setVisibility(View.GONE);
                        }
                    });

                    holder.card.setOnClickListener(v -> {

                        Intent i=new Intent(context,media_show.class);
                        i.putExtra("slider",fullgalres);
                        i.putExtra("cp",position);
                        appCompat.startActivity(i);

                    });

                    break;

            }


        }catch (Exception e){
        } catch (Throwable throwable) {
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
