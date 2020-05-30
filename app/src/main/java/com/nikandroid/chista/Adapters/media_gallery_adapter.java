package com.nikandroid.chista.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.nikandroid.chista.R;
import com.nikandroid.chista.Utils.App;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class media_gallery_adapter extends PagerAdapter {

    private JSONObject slide;
    Context context;
    AppCompatActivity appCompat;
    media_gallery_adapter(JSONObject s,Context c){
        context=c;
        appCompat=(AppCompatActivity)context;
        slide=s;
    }

    @Override
    public int getCount() {
        return slide.length();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater in=appCompat.getLayoutInflater();
        View row=in.inflate(R.layout.media_show_row,container,false);


        ImageView img= row.findViewById(R.id.media_show_row_image);
        VideoView vid= row.findViewById(R.id.media_show_row_video);
        try {
            final JSONObject tmp = new JSONObject(slide.getString(position+""));


            switch (tmp.getString("type")){

                case "video":
                    img.setVisibility(View.GONE);
                    String uriPath = tmp.getString("file_path"); //update package name
                    Uri uri = Uri.parse(uriPath);
                    MediaController mediacontroller = new MediaController(context);
                    vid.setMediaController(mediacontroller);
                    vid.setVideoURI(uri);
                    vid.requestFocus();
                    vid.setZOrderOnTop(true);
                    vid.start();
                    vid.setOnPreparedListener(mp -> {
                    });

                    break;

                case "image":
                    vid.setVisibility(View.GONE);
                    Picasso.get().load(tmp.getString("file_path")).into(img, new Callback() {
                        @Override
                        public void onSuccess() {
                            //prog.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            //prog.setVisibility(View.GONE);
                        }
                    });

                    break;
            }

        } catch (JSONException e) {
        }

        container.addView(row);
        return row;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}

