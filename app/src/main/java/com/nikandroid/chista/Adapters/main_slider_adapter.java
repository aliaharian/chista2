package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.nikandroid.chista.Functions.Function;
import com.nikandroid.chista.Params.Params;
import com.nikandroid.chista.R;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class main_slider_adapter extends PagerAdapter {

    private JSONObject slide;
    private AppCompatActivity appCompat;
    private Context context;
    private Function fun;
    public main_slider_adapter(JSONObject s, Context c, Function fun){
        slide=s;
        context=c;
        appCompat=(AppCompatActivity)c;
        this.fun=fun;

    }

    @Override
    public int getCount() {
        return slide.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater in=appCompat.getLayoutInflater();
        View row=in.inflate(R.layout.main_slider_line,container,false);
        ImageView img=(ImageView) row.findViewById(R.id.main_slider_page_image);
        try {
            final JSONObject tmp = new JSONObject(slide.getString("slide"+(position+1)));
            final String act=tmp.getString("act");
            final String actval=tmp.getString("act_val");
            Log.e("sliderpic",Params.pic_slider+tmp.getString("image"));
            Picasso
                    .get()
                    .load(Params.pic_slider+tmp.getString("image"))
                    .error(R.drawable.blank_pic)
                    .placeholder(R.drawable.blank_pic)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .into(img);

            row.setOnClickListener(view -> fun.intent_actions(act,actval));

        } catch (JSONException e) {
        }

        container.addView(row);
        return row;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}

