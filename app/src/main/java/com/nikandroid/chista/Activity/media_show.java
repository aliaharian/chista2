package com.nikandroid.chista.Activity;

import android.content.Context;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nikandroid.chista.R;
import com.skydoves.elasticviews.ElasticImageView;
import com.skydoves.elasticviews.ElasticLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class media_show extends AppCompatActivity {


    ProgressBar prog;
    ViewPager pager;
    ElasticImageView back;
    ElasticLayout next,per;
    int cp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_show);

        init();
        make_slider(getIntent().getExtras().getString("slider"),getIntent().getExtras().getInt("cp"));

    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));    }

    private void init(){

        getSupportActionBar().hide();
        final Window window = getWindow();
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.siyah));
        }
        pager=findViewById(R.id.media_show_pager);
        back=findViewById(R.id.media_show_back);
        next=findViewById(R.id.media_show_next);
        per=findViewById(R.id.media_show_per);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem()+1);
            }
        });
        per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(pager.getCurrentItem()-1);
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private Context getActivity(){
        return media_show.this;
    }


    private void make_slider(String sliderval, final int position){

        try {
            JSONObject rows = new JSONObject(sliderval);
            final media_gallery_adapter ppssdf=new media_gallery_adapter(rows);
            pager.setAdapter(ppssdf);
            pager.postDelayed(() -> pager.setCurrentItem(position),1500);
        }catch (Exception e){

        }



    }
    class media_gallery_adapter extends PagerAdapter {

        private JSONObject slide;

        media_gallery_adapter(JSONObject s){

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

            LayoutInflater in=getLayoutInflater();
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
                        MediaController mediacontroller = new MediaController(getActivity());
                        vid.setMediaController(mediacontroller);
                        vid.setVideoURI(uri);
                        vid.requestFocus();
                        vid.setZOrderOnTop(true);
                        vid.seekTo( 10 );
                        //vid.start();

                        vid.setOnPreparedListener(mp -> {
                           // prog.setVisibility(View.GONE);
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







}
